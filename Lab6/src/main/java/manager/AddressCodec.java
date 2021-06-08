package manager;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import model.Address;

public class AddressCodec extends MappingCodec<UdtValue, Address> {
    public AddressCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(Address.class));
    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected Address innerToOuter(@Nullable UdtValue address) {
        return address == null ? null : new Address(address.getString("street"),
                address.getInt("houseNumber"),
                address.getInt("flatNumber"));
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Address address) {
        return address == null ? null : getCqlType().newValue()
                .setString("street", address.getStreet())
                .setInt("houseNumber", address.getHouseNumber())
                .setInt("flatNumber", address.getFlatNumber());
    }
}
