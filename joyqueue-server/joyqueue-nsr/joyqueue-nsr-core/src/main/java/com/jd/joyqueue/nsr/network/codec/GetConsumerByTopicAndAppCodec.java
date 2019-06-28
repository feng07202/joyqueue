/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.joyqueue.nsr.network.codec;

import com.jd.joyqueue.domain.TopicName;
import com.jd.joyqueue.network.serializer.Serializer;
import com.jd.joyqueue.network.transport.command.Header;
import com.jd.joyqueue.network.transport.command.Type;
import com.jd.joyqueue.nsr.network.NsrPayloadCodec;
import com.jd.joyqueue.nsr.network.command.GetConsumerByTopicAndApp;
import com.jd.joyqueue.nsr.network.command.NsrCommandType;
import io.netty.buffer.ByteBuf;

/**
 * @author wylixiaobin
 * Date: 2019/1/27
 */
public class GetConsumerByTopicAndAppCodec implements NsrPayloadCodec<GetConsumerByTopicAndApp>, Type {
    @Override
    public GetConsumerByTopicAndApp decode(Header header, ByteBuf buffer) throws Exception {
        return new GetConsumerByTopicAndApp().app(Serializer.readString(buffer)).topic(TopicName.parse(Serializer.readString(buffer)));
    }

    @Override
    public void encode(GetConsumerByTopicAndApp payload, ByteBuf buffer) throws Exception {
        Serializer.write(payload.getApp(),buffer);
        Serializer.write(payload.getTopic().getFullName(),buffer);
    }

    @Override
    public int type() {
        return NsrCommandType.GET_CONSUMER_BY_TOPIC_AND_APP;
    }
}