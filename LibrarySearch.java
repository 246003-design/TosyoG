package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySearch {

    public static void main(String[] args) {
        // システムと模擬データベースの初期化
        Database db = new Database();
        LibrarySearchSystem system = new LibrarySearchSystem(db);
        User user = new User(system);

        // 利用者が図書検索フローを開始する
        user.startBookSearch();
    }

	public static List<entity.BookInfo> searchBooks(String trim) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}

// ==========================================
// 1. 利用者 (User) の役割
// ==========================================
class User {
    private final LibrarySearchSystem system;
    private final Scanner scanner;

    public User(LibrarySearchSystem system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    /**
     * 図書検索を要求する
     */
    public void startBookSearch() {
        // 図書管理システムに検索画面の表示を要求
        system.displaySearchScreen();
        
        while (true) {
            // 図書名、著者名、分類等の条件を入力する
            System.out.println("\n--- [利用者] 検索条件を入力してください ---");
            System.out.print("図書名 または 著者名など: ");
            String inputKeyword = scanner.nextLine();

            // 検索ボタンを押す
            System.out.println("[利用者] 検索ボタンを押しました。");
            
            // 入力内容をシステムに渡して検索処理を実行
            // システムから「再入力要求」があった場合はループが継続します
            boolean isSuccess = system.executeSearch(inputKeyword);
            if (isSuccess) {
                break; // 検索完了（または一覧表示）したら終了
            }
        }
    }
}

// ==========================================
// 2. 図書管理システム (LibrarySearchSystem) の役割
// ==========================================
class LibrarySearchSystem {
    private final Database database;

    public LibrarySearchSystem(Database database) {
        this.database = database;
    }

    /**
     * 図書検索画面を表示する
     */
    public void displaySearchScreen() {
        System.out.println("[システム] 図書検索画面を表示しました。");
    }

    /**
     * 再入力するよう表示する
     */
    public void displayRetryMessage() {
        System.out.println("[システム] 【警告】検索キーワードが空です。再入力してください。");
    }

    /**
     * 検索を実行する（分岐ロジック）
     */
    public boolean executeSearch(String inputContent) {
        // 分岐：検索内容は空か？
        if (inputContent == null || inputContent.trim().isEmpty()) {
            // 空の場合：再入力するよう表示する -> 図書検索画面を表示する
            displayRetryMessage();
            displaySearchScreen();
            return false; // 再入力が必要
        }

        // 空でない場合：入力内容から図書情報を取得する
        System.out.println("[システム] 入力内容を受け付けました。DBへ照会します。");
        List<BookInfo> results = database.searchBooks(inputContent);

        // 検索結果を一覧表示する
        displaySearchResults(results);
        return true; // 検索処理完了
    }

    /**
     * 検索結果を一覧表示する
     */
    private void displaySearchResults(List<BookInfo> results) {
        System.out.println("\n==========================================");
        System.out.println("[システム] 検索結果一覧");
        System.out.println("==========================================");
        if (results.isEmpty()) {
            System.out.println("該当する図書は見つかりませんでした。");
        } else {
            for (BookInfo book : results) {
                System.out.printf("図書名: %s | ISBN: %s | 分類: %s | 著者: %s | 出版社: %s | 状態: %s\n",
                        book.title, book.isbn, book.category, book.author, book.publisher, book.status);
            }
        }
        System.out.println("==========================================");
    }
}

// ==========================================
// 3. DB (Database) とデータ構造
// ==========================================

/**
 * データストア：図書情報
 */
class BookInfo {
    String title;       // 図書名
    String isbn;        // ISBN番号
    String category;    // 分類
    String author;      // 著者
    String publisher;   // 出版社
    String status;      // 図書状態

    public BookInfo(String title, String isbn, String category, String author, String publisher, String status) {
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }
}

class Database {
    private final List<BookInfo> bookTable = new ArrayList<>();

    public Database() {
        // 模擬データ（図書情報）の投入
        bookTable.add(new BookInfo("Javaプログラミング入門", "978-4-0000", "技術書", "山田太郎", "技術出版", "貸出可能"));
        bookTable.add(new BookInfo("オブジェクト指向設計", "978-4-1111", "技術書", "佐藤次郎", "開発社", "貸出中"));
        bookTable.add(new BookInfo("吾輩は猫である", "978-4-2222", "文学", "夏目漱石", "文学堂", "残数あり"));
    }

    /**
     * 入力内容（キーワード）に合致する図書情報を取得する
     */
    public List<BookInfo> searchBooks(String keyword) {
        List<BookInfo> matches = new ArrayList<>();
        // 入力内容（図書名、著者名、分類等）で部分一致検索を行う
        for (BookInfo book : bookTable) {
            if (book.title.contains(keyword) || 
                book.author.contains(keyword) || 
                book.category.contains(keyword)) {
                matches.add(book);
            }
        }
        return matches;
    }
}
