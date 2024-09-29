package graduation.petshop.domain.chat.repository;
import graduation.petshop.domain.chat.entity.ChatRoom;
import graduation.petshop.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT c FROM ChatRoom c WHERE (c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1)")
    Optional<ChatRoom> findByUser1AndUser2(@Param(value = "user1") Profile user1,@Param(value = "user2") Profile user2);

    List<ChatRoom> findByUser1OrUser2(@Param(value = "user1") Profile user1,@Param(value = "user2") Profile user2);


}