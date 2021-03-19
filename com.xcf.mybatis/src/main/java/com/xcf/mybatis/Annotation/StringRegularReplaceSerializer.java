package com.xcf.mybatis.Annotation;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
/** 
* @author xcf 
* @Date 创建时间：2021年3月19日 上午10:48:57 
*/
public class StringRegularReplaceSerializer extends JsonSerializer<String> {

    private final JsonRegularReplace jsonRegularReplace;

    public StringRegularReplaceSerializer(JsonRegularReplace jsonRegularReplace) {
        this.jsonRegularReplace = jsonRegularReplace;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        String replaceEl = jsonRegularReplace.canReplaceEl();
        String groupRegular = jsonRegularReplace.groupRegular();
        String replacement = jsonRegularReplace.replacement();
        String str = value;
        // 判空
        if (StringUtils.isNotBlank(groupRegular) &&
            StringUtils.isNotBlank(replacement)) {
            // 计算是否可以执行替换,默认关闭
            boolean canReplace = false;
            if (StringUtils.isNotBlank(replaceEl)) {
                canReplace = SpringElExpressionUtils.getBoolean(null, replaceEl, () -> false);
            }
            if (canReplace) {
                str = value.replaceAll(groupRegular, replacement);
            }
        }

        gen.writeString(str);
    }
}
