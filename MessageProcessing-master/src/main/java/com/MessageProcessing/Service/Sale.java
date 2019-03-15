package com.MessageProcessing.Service;
/**
 * @author swati 
 * Sale class processes each product sales and appends them in a
 *         sales log. Uses a message parser to parse the incoming messages and
 *         collects the product information and prepares a product object for
 *         sale processing. Ignores processing of any invalid messages and
 *         processes until it reaches the log report limit.
 */
public class Sale {

	public SaleLog log;

	private AdjustPrice adjustPrice;

	private Product product;

	public Sale() {
		log = new SaleLog();
	}

	/**
	 * Process notices and appends product information to the relevant product
	 * in the SaleLog.
	 * 
	 * @param saleNotice
	 * @return [Boolean] false on empty string and invalid message and true on
	 *         the rest.
	 */
	public boolean processNotification(String saleNotice) {

		MessageParser messageParser;
		messageParser = new MessageParser(saleNotice);
		String productType = messageParser.getProductType();

		if (productType.isEmpty()) {
			return false;
		}

		this.product = log.getProduct(productType);
		this.adjustPrice = new AdjustPrice(product);
		this.product.setProductQuantity(messageParser.getProductQuantity());
		this.product.setTotalQuantity(messageParser.getProductQuantity());
		this.product.setProductPrice(messageParser.getProductPrice());
		this.product.setAdjustmentOperator(messageParser.getOperatorType());

		setProductTotalPrice();
		log.setNormalReports(saleNotice);

		log.updateProduct(product);

		return true;
	}

	/**
	 * Set or Append Total product price based on any adjustment if given. Also
	 * appends the log for adjustments made.
	 */
	private void setProductTotalPrice() {
		double adjustedPrice;
		double productValue;

		if (!product.getAdjustmentOperator().isEmpty()) {
			adjustedPrice = adjustPrice.getAdjustedPrice();
			log.setAdjustmentReports(adjustPrice.adjustmentReport());
			product.setTotalPrice(adjustedPrice);
		} else {
			productValue = product.calculatePrice(product.getProductQuantity(), product.getProductPrice());
			product.appendTotalPrice(productValue);
		}
	}



}
