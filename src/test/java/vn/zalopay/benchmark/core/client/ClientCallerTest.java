package vn.zalopay.benchmark.core.client;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContextBuilder;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import vn.zalopay.benchmark.core.BaseTest;
import vn.zalopay.benchmark.core.ClientCaller;
import vn.zalopay.benchmark.core.protobuf.ProtocInvoker;
import vn.zalopay.benchmark.core.specification.GrpcResponse;

import javax.net.ssl.SSLException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockConstructionWithAnswer;


public class ClientCallerTest extends BaseTest {
    static int countMockFailedALPN = 0;

    @Test
    public void testCanSendGrpcUnaryRequest() {
        clientCaller = new ClientCaller(HOST_PORT, null,
                FULL_METHOD, false, false);
        clientCaller.buildRequestAndMetadata(REQUEST_JSON, "key1:1,key2:2");
        GrpcResponse resp = clientCaller.call("5000");
        clientCaller.shutdownNettyChannel();
        Assert.assertNotNull(resp);
        Assert.assertTrue(resp.getGrpcMessageString().contains("\"theme\": \"Hello server"));
    }
}
