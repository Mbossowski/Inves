import React from "react";
import './NewsItem.css';

const NewsItem = (news) => {
   
  return (
    <div className="news-item">
      <h3 className="news-title">{news.news.title}</h3>
      <span className="news-topic">{news.news.topic}</span><br></br>
      <span className="news-date">Published: {news.news.publishedDate}, </span>
      <span className="news-source"> Source: {news.news.source}</span><br></br>
      <a
        className="news-link"
        href={news.news.url}
        target="_blank"
        rel="noopener noreferrer"
      >
        Read more
      </a>
    </div>
  );
};

export default NewsItem;
