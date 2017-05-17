package com.wooou.library.updateApp;

/**
 * Created by Lan Long on 2017/5/17 15:12.
 */
public interface ParserInterface<Bean extends BeanInterface> {

    String formatToString(FormatType type);
    Bean parseToBean(Bean bean);

}
