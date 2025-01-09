export class Stock {
  constructor(
    id,
    symbol,
    companyName,
    lastSale,
    netChange,
    pctChange,
    fiftyTwoWeekHigh,
    fiftyTwoWeekLow,
    regularMarketDayHigh,
    regularMarketDayLow,
    previousClose,
    volume,
    market,
    country,
    industry,
    sector,
  ) {
    this.id = id;
    this.symbol = symbol;
    this.companyName = companyName;
    this.lastSale = lastSale;
    this.netChange = netChange;
    this.pctChange = pctChange;
    this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    this.regularMarketDayHigh = regularMarketDayHigh;
    this.regularMarketDayLow = regularMarketDayLow;
    this.previousClose = previousClose;
    this.volume = volume;
    this.market = market;
    this.country = country;
    this.industry = industry;
    this.sector = sector;
  }
  static fromResponse(response) {
    return new Stock(
      response.id,
      response.symbol,
      response.companyName,
      response.lastSale,
      response.netChange,
      response.pctChange,
      response.fiftyTwoWeekHigh,
      response.fiftyTwoWeekLow,
      response.regularMarketDayHigh,
      response.regularMarketDayLow,
      response.previousClose,
      response.volume,
      response.market,
      response.country,
      response.industry,
      response.sector
    );
  }
}