package pbrpc;

import java.util.concurrent.atomic.AtomicLong;

import com.google.protobuf.ServiceException;

import io.dcos.dos.MasterServer;
import io.dcos.dos.MasterServer.GetJobRequest;
import io.dcos.dos.MasterServer.GetJobResponse;
import io.dcos.dos.MasterServer.Master.BlockingInterface;
import io.idcos.pbrpc.FakeRpcController;
import io.idcos.pbrpc.NettyConnection;
import io.idcos.pbrpc.SyncRpcChannel;

public class TestDosMaster {

	public static void main(String[] args) {
		NettyConnection conn = new NettyConnection();
		conn.start("118.193.197.176", 9527);
		AtomicLong counter = new AtomicLong();
		BlockingInterface stub = MasterServer.Master.newBlockingStub(new SyncRpcChannel(conn,counter));
		GetJobRequest request = MasterServer.GetJobRequest.newBuilder().setName("redis").build();
		try {
			GetJobResponse response = stub.getJob(new FakeRpcController(), request);
			System.out.println(response.getJob().getName());
			response = stub.getJob(new FakeRpcController(), request);
			System.out.println(response.getJob().getName());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

}