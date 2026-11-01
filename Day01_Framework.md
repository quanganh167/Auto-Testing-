# Day 01: Tìm hiểu về Automation Test

## 1. Cucumber, Serenity BDD và Screenplay

Cucumber, Serenity BDD và Screenplay có thể kết hợp để xây dựng bộ kiểm thử tự động bằng Java. Mỗi thành phần có vai trò riêng:

- **Cucumber:** thực thi kịch bản viết bằng Gherkin, thường theo cấu trúc Given, When, Then.
- **Serenity BDD:** ghi nhận các bước kiểm thử, tổng hợp kết quả và tạo báo cáo.
- **Screenplay:** tổ chức code theo Actor, nhiệm vụ và thông tin cần kiểm tra.

Kịch bản được lưu trong file `.feature`. Step Definitions liên kết từng bước trong kịch bản với phương thức Java tương ứng.

Các khái niệm cơ bản:

- **Given:** điều kiện hoặc trạng thái ban đầu.
- **When:** hành động cần kiểm tra.
- **Then:** kết quả mong đợi.
- **Actor:** đối tượng thực hiện kịch bản.
- **Ability:** khả năng tương tác, chẳng hạn sử dụng trình duyệt.
- **Task:** nhiệm vụ cần thực hiện.
- **Interaction:** thao tác cụ thể như nhập dữ liệu hoặc nhấn nút.
- **Question:** lấy thông tin về trạng thái hệ thống.

Trong tích hợp WebDriver, `Target` xác định phần tử giao diện. Assertion đối chiếu kết quả thực tế với kết quả mong đợi; Serenity hỗ trợ thực hiện bằng `Ensure`.

Bài hướng dẫn thực hành trên TodoMVC: mở danh sách, thêm công việc và kiểm tra công việc xuất hiện. Quy trình gồm viết kịch bản, triển khai Step Definitions, xác định phần tử, thực hiện nhiệm vụ và kiểm tra kết quả.

Chạy bài kiểm thử bằng `mvn clean verify`. Báo cáo của bài mẫu nằm tại `target/site/serenity/index.html`.

Em nhận thấy cách tổ chức này giúp kịch bản dễ đọc và tái sử dụng các nhiệm vụ. Tuy nhiên, người viết cần hiểu Java, Gherkin và xác định kết quả mong đợi rõ ràng để viết assertion.

Nguồn:
- https://serenity-bdd.github.io/docs/tutorials/cucumber-screenplay
- https://serenity-bdd.github.io/docs/screenplay/screenplay_fundamentals

## 2. Playwright

Playwright hỗ trợ tự động hóa trình duyệt để kiểm tra ứng dụng web. Playwright Test tích hợp trình chạy test, assertion, cơ chế cô lập trạng thái, chạy song song và báo cáo.

Trang hướng dẫn sử dụng JavaScript hoặc TypeScript trên Node.js. Playwright hỗ trợ Chromium, Firefox và WebKit.

Các thành phần thường dùng:

- **test():** khai báo bài kiểm thử.
- **page:** tương tác với một trang.
- **locator:** tìm phần tử cần thao tác hoặc kiểm tra.
- **expect():** xác nhận kết quả bằng assertion.
- **fixture:** chuẩn bị và thu dọn tài nguyên cho test.
- **request:** gửi HTTP request để kiểm thử API.

Một số khả năng nổi bật:

- **Auto-waiting:** chờ phần tử đạt điều kiện phù hợp trước khi thao tác.
- **Assertion hỗ trợ retry:** kiểm tra lại trạng thái đến khi đạt điều kiện hoặc hết thời gian chờ.
- **Cô lập test:** cung cấp Browser Context riêng cho mỗi test; dữ liệu trên máy chủ vẫn cần quản lý riêng.
- **Trace Viewer:** xem lại lần chạy đã ghi để phân tích lỗi.

Khởi tạo dự án bằng `npm init playwright@latest`. Các file chính gồm `playwright.config.ts` để cấu hình, `package.json` để quản lý thư viện và thư mục `tests` chứa bài kiểm thử.

Các lệnh thường dùng:

- `npx playwright test`: chạy test.
- `npx playwright test --headed`: chạy có hiển thị trình duyệt.
- `npx playwright test --ui`: mở giao diện kiểm thử.
- `npx playwright show-report`: xem báo cáo HTML.

Em nhận thấy Playwright thuận tiện khi viết, chạy và phân tích test. Dù có cơ chế tự chờ, người viết vẫn phải chuẩn bị dữ liệu, chọn locator chính xác và kiểm tra kết quả nghiệp vụ.

Nguồn:
- https://playwright.dev/docs/intro
- https://playwright.dev/docs/writing-tests
- https://playwright.dev/docs/trace-viewer



## 3. Câu hỏi

### Automation test là gì?

Automation test là sử dụng code và công cụ để tự động thực hiện các bước kiểm thử, thu thập kết quả thực tế và đối chiếu với kết quả mong đợi.

Một kịch bản cần có điều kiện ban đầu, dữ liệu, hành động và assertion. Ví dụ, sau khi thêm công việc, test phải kiểm tra danh sách có công việc vừa nhập.

### Tại sao cần automation test?

Automation test giúp:

- Giảm thời gian thực hiện các phép kiểm tra lặp lại.
- Tăng tính nhất quán của thao tác và điều kiện kiểm tra.
- Phát hiện chức năng cũ bị ảnh hưởng sau khi code thay đổi.
- Phản hồi sớm khi tích hợp vào CI/CD.
- Tái sử dụng các bước và cung cấp báo cáo để phân tích lỗi.

Automation test cần thời gian xây dựng và bảo trì. Vì vậy, nên ưu tiên trường hợp quan trọng, thường xuyên lặp lại và có kết quả mong đợi rõ ràng.

### Khi nào cần chạy kịch bản automation test?

Có thể chạy trong các trường hợp:

- Khi phát triển chức năng hoặc sửa lỗi.
- Khi cập nhật pull request hoặc tích hợp code.
- Khi có bản build mới trên môi trường kiểm thử.
- Khi kiểm thử hồi quy hoặc trước khi phát hành.
- Theo lịch, chẳng hạn chạy hằng đêm.

CI/CD có thể tự động kích hoạt các lần chạy. Nhóm cần chọn phạm vi test phù hợp với thay đổi và bảo đảm môi trường, dữ liệu đáp ứng điều kiện trước khi chạy.
