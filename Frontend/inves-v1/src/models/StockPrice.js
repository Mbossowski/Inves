export class StockPrice {
    constructor(
      id,
      symbol,
      lastSale,
      datetime,
    ) {
      this.id = id;
      this.symbol = symbol;
      this.lastSale = lastSale;
      this.datetime = datetime;
    }
    static fromResponse(response) {
      return new Stock(
        response.id,
        response.symbol,
        response.lastSale,
        response.datetime,
      );
    }
  }