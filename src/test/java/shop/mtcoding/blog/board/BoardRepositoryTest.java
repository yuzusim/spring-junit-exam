package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class) // 내가 만든 클래스는 @Import() 해줘야함
@DataJpaTest // DB 관련 객체들이 IoC에 뜬다. (DataJpaTest 안적으면 테스트를 못한다.)
public class BoardRepositoryTest {

    @Autowired // TEST에서 DI하는 코드
    private BoardRepository boardRepository;


    @Test
    public void selectAll_test(){

        // when
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());


        // then (상태 검사) id=1, title=제목1, content=내용1, author=홍길동
        System.out.println(boardList);
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("제목1");
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("내용1");
        Assertions.assertThat(boardList.get(0).getAuthor()).isEqualTo("홍길동");
        Assertions.assertThat(boardList.size()).isEqualTo(8);
    }

    @Test
    public void selectOne_test() {
        // given
        int id = 1;

        // when
        Board board = boardRepository.selectOne(id);

        // then (상태 검사)
        // System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1"); // getTitle() 앞에 실제 엑추얼 데이터
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("홍길동");

    }

    @Test
    public void insert_test() { // 테스트 메서드는 파라미터가 없다. 리턴도 없다.
        // given
        String title = "제목10";
        String content = "내용10";
        String author = "이순신";

        // when
        boardRepository.insert(title, content, author);

        // then -> 눈으로 확인 (쿼리)
    } // Rollback (자동)

    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        boardRepository.deleteById(id);

        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());


    }

    @Test
    public void update_test(){
        // given
        String title = "하이1";
        String content = "반가워1";
        String auther = "이성계";
        int id = 1;

        // when
        boardRepository.update(title, content, auther);

        // then
        Board board = boardRepository.selectOne(id);
        Assertions.assertThat(board.getTitle()).isEqualTo("하이1");
        Assertions.assertThat(board.getContent()).isEqualTo("반가워1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("이성계");


    }


}

// 테스트시 디비는 H2로 쓴다.

