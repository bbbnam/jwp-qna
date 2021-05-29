package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    private DeleteHistory deleteHistory;
    private DeleteHistory saved;

    @BeforeEach
    void setUp() {
        deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 1L, LocalDateTime.now());
        saved = deleteHistoryRepository.save(deleteHistory);
    }

    @Test
    @DisplayName("DeleteHistory 저장 테스트")
    void save() {
        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getContentId()).isEqualTo(deleteHistory.getContentId()),
                () -> assertThat(saved.getDeletedById()).isEqualTo(deleteHistory.getDeletedById()),
                () -> assertThat(saved.getContentType()).isEqualTo(deleteHistory.getContentType()),
                () -> assertThat(saved.getCreateDate()).isEqualTo(deleteHistory.getCreateDate())
        );
    }

    @Test
    @DisplayName("DeleteHistory 제거 테스트")
    void delete() {
        deleteHistoryRepository.delete(saved);

        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();

        assertThat(deleteHistories.contains(saved)).isFalse();
    }

    @Test
    @DisplayName("DeleteHistory 조회 테스트")
    void find() {
        Optional<DeleteHistory> finded = deleteHistoryRepository.findById(saved.getId());

        assertAll(
                () -> assertThat(finded.isPresent()).isTrue(),
                () -> assertThat(finded.get()).isEqualTo(saved)
        );
    }
}
