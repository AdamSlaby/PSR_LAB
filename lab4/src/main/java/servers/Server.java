package servers;

import java.net.UnknownHostException;

import com.hazelcast.config.Config;
import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.partition.MigrationListener;
import com.hazelcast.partition.MigrationState;
import com.hazelcast.partition.PartitionService;
import com.hazelcast.partition.ReplicaMigrationEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import config.HConfig;
import model.*;

public class Server
{
    public static void main(String[] args) throws UnknownHostException {
        Config config = HConfig.getConfig();

		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

		instance.addDistributedObjectListener(new DistributedObjectListener() {

			@Override
			public void distributedObjectDestroyed(DistributedObjectEvent e) {
				System.out.println(e);
			}

			@Override
			public void distributedObjectCreated(DistributedObjectEvent e) {
				System.out.println(e);
			}
		});

		instance.getCluster().addMembershipListener(new MembershipListener() {

			@Override
			public void memberRemoved(MembershipEvent e) {
				System.out.println(e);
			}

			@Override
			public void memberAdded(MembershipEvent e) {
				System.out.println(e);
			}
		});

		PartitionService partitionService = instance.getPartitionService();
		partitionService.addMigrationListener(new MigrationListener() {
			
			@Override
			public void replicaMigrationFailed(ReplicaMigrationEvent e) {
				System.out.println(e);
			}
			
			@Override
			public void replicaMigrationCompleted(ReplicaMigrationEvent e) {
				System.out.println(e);
			}
			
			@Override
			public void migrationStarted(MigrationState s) {
				System.out.println(s);
			}
			
			@Override
			public void migrationFinished(MigrationState s) {
				System.out.println(s);
			}
		});

		IMap<String, Vehicle> vehicles = instance.getMap("vehicles");

		vehicles.addEntryListener(new EntryAddedListener<String, Vehicle>() {

			@Override
			public void entryAdded(EntryEvent<String, Vehicle> e) {
				System.out.println(e);
			}
		}, true);

		IMap<Long, Unit> units = instance.getMap("units");

		units.addEntryListener(new EntryAddedListener<Long, Unit>() {

			@Override
			public void entryAdded(EntryEvent<Long, Unit> e) {
				System.out.println(e);
			}
		}, true);

		IMap<String, Employee> employees = instance.getMap("employees");

		units.addEntryListener(new EntryAddedListener<String, Employee>() {

			@Override
			public void entryAdded(EntryEvent<String, Employee> e) {
				System.out.println(e);
			}
		}, true);
	}


}