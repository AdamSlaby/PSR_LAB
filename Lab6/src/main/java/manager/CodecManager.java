package manager;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;

public class CodecManager extends Manager{
    public CodecManager(CqlSession session) {
        super(session);
    }

    public void registerAddressCodec() {
        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();
        UserDefinedType coordinatesUdt =
                session
                        .getMetadata()
                        .getKeyspace("world")
                        .flatMap(ks -> ks.getUserDefinedType("address"))
                        .orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> typeCodec = codecRegistry.codecFor(coordinatesUdt);
        AddressCodec addressCodec = new AddressCodec(typeCodec);
        ((MutableCodecRegistry) codecRegistry).register(addressCodec);
    }
}
