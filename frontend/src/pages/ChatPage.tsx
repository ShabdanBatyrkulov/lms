import React from 'react';
import Chat from '../components/Chat';
import styles from './ChatPage.module.css';

const ChatPage: React.FC = () => {
    return (
        <div className={styles.container}>
            <h1 className={styles.title}>AI Chat Assistant</h1>
            <div className={styles.chatWrapper}>
                <Chat />
            </div>
        </div>
    );
};

export default ChatPage;