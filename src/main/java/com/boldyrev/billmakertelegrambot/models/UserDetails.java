package com.boldyrev.billmakertelegrambot.models;

import com.boldyrev.billmakertelegrambot.dto.BillDetails;
import com.boldyrev.billmakertelegrambot.dto.BillOwnerDetails;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

@Data
@AllArgsConstructor
public class UserDetails {

    @ToString.Exclude
    private User user;

    private BillOwnerDetails billOwnerDetails;

    private BillDetails billDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDetails)) {
            return false;
        }
        UserDetails that = (UserDetails) o;
        return that.getUser().getId() == this.getUser().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId());
    }
}
