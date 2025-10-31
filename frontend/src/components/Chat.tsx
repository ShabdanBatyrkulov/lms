import React, { useState, useRef, useEffect } from 'react';
import ReactMarkdown from 'react-markdown';
import styles from './Chat.module.css';
import api from '../utils/api';

interface Message {
    content: string;
    timestamp: string;
    isUser: boolean;
}

interface ChatProps {
    className?: string;
}

const Chat: React.FC<ChatProps> = ({ className = '' }) => {
    const [messages, setMessages] = useState<Message[]>([]);
    const [inputMessage, setInputMessage] = useState('');
    const messagesEndRef = useRef<HTMLDivElement>(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        if (!inputMessage.trim()) return;

        // Add user message
        const userMessage: Message = {
            content: inputMessage,
            timestamp: new Date().toISOString(),
            isUser: true
        };

        setMessages(prev => [...prev, userMessage]);
        setInputMessage('');

        try {
            const response = await api.post('/chat', { message: inputMessage });
            
            // Add AI response
            const aiMessage: Message = {
                content: response.data.response,
                timestamp: response.data.timestamp,
                isUser: false
            };

            setMessages(prev => [...prev, aiMessage]);
        } catch (error: any) {
            console.error('Error sending message:', error);
            if (error.response?.status === 401) {
                // Handled by axios interceptor - will redirect to login
            } else {
                // Show error message to user
                alert('Failed to send message. Please try again.');
            }
        }
    };

    return (
        <div className={`${styles.chatContainer} ${className}`}>
            <div className={styles.messagesContainer}>
                {messages.map((message, index) => (
                    <div 
                        key={index}
                        className={`${styles.message} ${
                            message.isUser ? styles.userMessage : styles.aiMessage
                        }`}
                    >
                        <div className={styles.messageContent}>
                            {message.isUser ? (
                                message.content
                            ) : (
                                <ReactMarkdown>{message.content}</ReactMarkdown>
                            )}
                        </div>
                        <div className={styles.timestamp}>
                            {new Date(message.timestamp).toLocaleTimeString()}
                        </div>
                    </div>
                ))}
                <div ref={messagesEndRef} />
            </div>
            <form onSubmit={handleSubmit} className={styles.inputContainer}>
                <input
                    type="text"
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    placeholder="Type your message..."
                    className={styles.input}
                />
                <button type="submit" className={styles.sendButton}>
                    Send
                </button>
            </form>
        </div>
    );
};

export default Chat;