package me.akamex.luckapi.chat.click;

import me.akamex.luckapi.LuckServiceApi;

public interface ChatClickActionService extends LuckServiceApi {

    ChatClickAction createAction(ChatClickAction action);

    ChatClickAction createAction(ChatClickAction.Builder builder);

}
