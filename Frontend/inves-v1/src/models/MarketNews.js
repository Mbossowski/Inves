export class MarketNews {
    constructor(title, publishedDate, topic, source, url) {
      this.title = title;
      this.publishedDate = publishedDate;
      this.topic = topic;
      this.source = source;
      this.url = url;
    }
  
    static fromResponse(response) {
      return new MarketNews(
        response.title,
        response.publishedDate,
        response.topic,
        response.source,
        response.url
      );
    }
  }
  