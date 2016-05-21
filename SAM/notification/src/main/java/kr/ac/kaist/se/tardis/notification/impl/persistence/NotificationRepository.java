package kr.ac.kaist.se.tardis.notification.impl.persistence;

import java.util.Set;

import kr.ac.kaist.se.tardis.notification.api.Notification;
import kr.ac.kaist.se.tardis.notification.impl.NotificationImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationImpl, Long>{

	Set<Notification> findNotificationsByUsername(String username);

}
