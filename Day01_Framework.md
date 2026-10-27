Day 01: Tìm hiểu framework và Automation Test
2. Tìm hiểu một vài framework
2.1. Cucumber using Serenity BDD and Screenplay
2.1.1. Tổng quan
Cucumber, Serenity BDD và Screenplay kết hợp để xây dựng bộ kiểm thử tự động bằng Java, trong đó mỗi thành phần có vai trò riêng:
Thành phần	Vai trò
Cucumber	Thực thi kịch bản Gherkin thông qua code tương ứng
Serenity BDD	Ghi nhận các bước, tổng hợp kết quả và tạo báo cáo
Screenplay	Tổ chức code theo Actor, nhiệm vụ và thông tin cần quan sát


Bài hướng dẫn thực hành trên TodoMVC với luồng mở danh sách, thêm công việc và kiểm tra công việc xuất hiện. Mục tiêu là chuyển mô tả hành vi thành bài kiểm thử có thể thực thi.
2.1.2. Các khái niệm chính
Kịch bản được lưu trong file .feature, thường gồm:
- Feature: chức năng được mô tả.
- Scenario: một tình huống cụ thể.
- Given: điều kiện hoặc trạng thái ban đầu.
- When: hành động cần kiểm tra.
- Then: kết quả mong đợi.
Step Definition kết nối từng bước Gherkin với phương thức Java. Ví dụ, bước thêm công việc nhận tên qua tham số rồi gọi nhiệm vụ thực hiện.

Trong Screenplay:
Khái niệm	Ý nghĩa
Actor	Đối tượng thực hiện kịch bản
Ability	Khả năng tương tác, chẳng hạn sử dụng trình duyệt
Task	Nhiệm vụ như thêm công việc
Interaction	Thao tác cụ thể như nhập nội dung, nhấn nút
Question	Lấy thông tin về trạng thái hệ thống


Trong tích hợp WebDriver, Target xác định phần tử giao diện. Question cung cấp thông tin; assertion đối chiếu thông tin đó với mong đợi. Serenity hỗ trợ kiểm tra bằng Ensure.
2.1.3. Môi trường và quy trình triển khai
Tutorial yêu cầu JDK 17 trở lên, Maven 3 trở lên, IDE hỗ trợ Java và Git.
Thư mục trong bài mẫu	Nội dung
src/test/resources/features	Kịch bản .feature
src/test/java/starter/stepdefinitions	Code liên kết các bước
src/test/java/starter/helpers	Lớp định nghĩa trang và nhiệm vụ hỗ trợ


Quy trình thực hiện:
1. Lấy dự án mẫu và viết kịch bản.
2. Triển khai Step Definitions.
3. Định nghĩa phần tử và nhiệm vụ.
4. Bổ sung assertion để kiểm tra kết quả.
5. Chạy mvn clean verify và xem báo cáo.
Báo cáo của bài mẫu nằm tại target/site/serenity/index.html. Báo cáo thể hiện kết quả và chi tiết kịch bản, góp phần tạo living documentation: tài liệu hành vi gắn với kiểm thử thực thi.
2.1.4. Nhận xét
Cách kết hợp này giúp kịch bản dễ đọc, nhiệm vụ có thể tái sử dụng và kết quả được trình bày theo hành vi kiểm thử. Người mới cần làm quen với Java, Gherkin và cách chia code Screenplay.

Khi áp dụng, em cần viết kịch bản rõ mục tiêu, chuẩn bị dữ liệu và xác định Expected Output cụ thể. Việc thao tác thành công chưa đủ kết luận chức năng đúng nếu thiếu assertion.

Nguồn:
- Bài hướng dẫn chính
- Screenplay Fundamentals
- Gherkin
- Step Definitions
- Serenity Ensure
- Serenity Reports
2.2. Playwright
2.2.1. Tổng quan
Playwright hỗ trợ tự động hóa trình duyệt để kiểm tra ứng dụng web. Playwright Test cung cấp trình chạy test, assertion, cô lập trạng thái, chạy song song và báo cáo.

Trang Intro hướng dẫn JavaScript hoặc TypeScript trên Node.js. Các trình duyệt được hỗ trợ gồm Chromium, Firefox và WebKit. Có thể chạy hiện cửa sổ trình duyệt hoặc chạy không hiển thị cửa sổ.
2.2.2. Các thành phần và khả năng chính
Thành phần	Chức năng
test()	Khai báo bài kiểm thử
page	Tương tác với một trang
locator	Tìm phần tử để thao tác hoặc kiểm tra
expect()	Kiểm tra kết quả bằng assertion
fixture	Chuẩn bị và thu dọn tài nguyên
request	Gửi HTTP request để kiểm thử API


Một số đặc điểm cần hiểu:
- Auto-waiting: chờ phần tử đạt điều kiện phù hợp trước thao tác. Sau thao tác vẫn cần kiểm tra kết quả nghiệp vụ.
- Assertion hỗ trợ retry: kiểm tra lại trạng thái đến khi đạt điều kiện hoặc hết thời gian chờ.
- Cô lập trạng thái: Playwright Test mặc định tạo Browser Context riêng cho mỗi test; dữ liệu trên máy chủ vẫn cần quản lý riêng.
- Kiểm thử API: gọi API trực tiếp, chuẩn bị dữ liệu hoặc xác nhận kết quả sau thao tác UI.
Locator phải nhận diện đúng phần tử. Những cách tìm thường dùng gồm getByRole(), getByLabel() và getByTestId().
2.2.3. Khởi tạo và cách chạy
Khởi tạo dự án bằng npm init playwright@latest, sau đó chọn ngôn ngữ, thư mục test và cài trình duyệt.
Tệp/thư mục	Vai trò
playwright.config.ts	Cấu hình trình duyệt, timeout, retry và báo cáo
package.json	Quản lý thư viện phụ thuộc
tests/	Chứa các bài kiểm thử


Quy trình cơ bản: chuẩn bị dữ liệu, mở trang bằng page.goto(), thao tác qua locator và kiểm tra bằng expect().
Mục đích	Lệnh
Chạy test	npx playwright test
Hiện trình duyệt	npx playwright test --headed
Mở giao diện kiểm thử	npx playwright test --ui
Xem báo cáo HTML	npx playwright show-report


2.2.4. Báo cáo và nhận xét
Báo cáo giúp xem trạng thái, lỗi và các bước chạy. Trace Viewer cho phép xem lại lần chạy đã ghi, gồm hành động, trạng thái trang và thông tin mạng để phân tích lỗi.

Em nhận thấy Playwright cung cấp nhiều chức năng trong cùng bộ công cụ, thuận tiện khi viết và theo dõi test. Tuy vậy, dữ liệu, locator và assertion vẫn cần thiết kế chính xác.

Khi chạy song song, các test phải tránh xung đột dữ liệu. Test thất bại rồi thành công khi retry được đánh dấu flaky và vẫn cần tìm nguyên nhân.

Nguồn:
- Bài hướng dẫn chính
- Writing tests
- Fixtures
- Auto-waiting
- Assertions
- API testing
- Trace Viewer
- Retries
3. Trả lời các câu hỏi
3.1. Automation test là gì?
Automation test là sử dụng code và công cụ để tự động thực hiện các bước kiểm thử, thu thập kết quả thực tế và đối chiếu với kết quả mong đợi.

Một kịch bản cần có điều kiện ban đầu, dữ liệu, hành động và assertion. Ví dụ, sau khi thêm công việc, test phải kiểm tra danh sách có công việc vừa nhập; chỉ nhập dữ liệu chưa đủ chứng minh chức năng đúng.
3.2. Tại sao cần auto test?
- Giảm thời gian chạy lặp lại: tự động thực hiện các bước đã lập trình.
- Tăng tính nhất quán: thực hiện cùng thao tác và điều kiện kiểm tra.
- Hỗ trợ hồi quy: phát hiện chức năng cũ bị ảnh hưởng sau thay đổi.
- Phản hồi sớm: có thể tự động chạy khi cập nhật code.
- Tái sử dụng và phân tích: dùng lại các thành phần, đọc báo cáo để xác định lỗi.
Automation test cần chi phí xây dựng và bảo trì. Vì vậy, nên ưu tiên trường hợp quan trọng, thường xuyên lặp lại và có kết quả mong đợi rõ ràng. Kiểm thử thủ công vẫn cần thiết khi khám phá ứng dụng hoặc đánh giá trải nghiệm.
3.3. Khi nào cần chạy kịch bản auto test?
Thời điểm	Mục đích
Khi phát triển hoặc sửa lỗi	Kiểm tra các chức năng liên quan
Khi cập nhật pull request hoặc tích hợp code	Phát hiện sai lệch trước hoặc sau tích hợp
Khi có bản build mới trên môi trường test	Kiểm tra nhanh các luồng quan trọng
Khi kiểm thử hồi quy hoặc trước phát hành	Kiểm tra ảnh hưởng và các chức năng trong phạm vi
Theo lịch, chẳng hạn hằng đêm	Chạy bộ test rộng hơn


CI/CD có thể tự động kích hoạt các lần chạy này. Phạm vi test cần phù hợp với thay đổi; không nhất thiết chạy toàn bộ sau mọi lần cập nhật. Trước khi chạy, môi trường và dữ liệu phải đáp ứng precondition.
