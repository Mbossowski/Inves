import { useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const WebSocketComponent = ({ setStocks }) => {
  useEffect(() => {
    // Use SockJS to connect to your WebSocket endpoint
    const client = new Client({
      webSocketFactory: () => new SockJS("https://streamer.finance.yahoo.com/"), // Backend WebSocket endpoint
      onConnect: () => {
        console.log("Connected to WebSocket");
        // Subscribe to the topic for stock updates
        client.subscribe("AAPL", (message) => {
          const updatedStocks = JSON.parse(message.body);
          setStocks(updatedStocks); // Update stock state
        });
      },
      onStompError: (frame) => {
        console.error("STOMP Error: " + frame.headers["message"]);
      },
    });

    client.activate(); // Activate the WebSocket client

    // Cleanup on component unmount
    return () => client.deactivate();
  }, [setStocks]);

  return null; // This component does not render anything
};

export default WebSocketComponent;
