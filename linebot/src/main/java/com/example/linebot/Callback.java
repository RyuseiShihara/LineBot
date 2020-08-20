package com.example.linebot;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.LocalTime;
import java.util.Random;

@LineMessageHandler
public class Callback {
    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    // フォローイベントに対応する
    @EventMapping
    public TextMessage handleFollow(FollowEvent event) {
        // 実際の開発ではユーザIDを返信せず、フォロワーのユーザIDをデータベースに格納しておくなど
        String userId = event.getSource().getUserId();
        return reply("あなたのユーザIDは " + userId);
    }

    // 返答メッセージを作る
    private TextMessage reply(String text) {
        return new TextMessage(text);
    }

    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        switch (text) {
            case "やあ":
                return greet();
            default:
                return reply(text);
        }
    }

    // あいさつする
    private TextMessage greet() {
        LocalTime lt = LocalTime.now();
        int hour = lt.getHour();
        if (hour >= 17) {
            return reply("こんばんは！");
        }
        if (hour >= 11) {
            return reply("こんにちは！");
        }
        return reply("おはよう！");
    }

}