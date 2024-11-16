## Phân tích yêu cầu
- Ứng dụng Bank Statement Analyzer (BSA) cần phải được đạt:
	* Đọc và xử lý file CSV: Nhận vào một file CSV từ ngân hàng, trong đó mỗi dòng mô tả một giao dịch bao gồm: ngày giao dịch,số tiền,danh mục
	* Tính toán tổng lợi nhuận và tổn thất: Tổng hợp và xác định xem số dư cuối cùng là dương hay âm.
	* Tính tổng số giao dịch trong một tháng cụ thể.
	* Xác định 10 giao dịch có giá trị chi tiêu lớn nhất.
	* Xác định danh mục chi tiêu cao nhất.

## Đề xuất giải pháp
1. Kiến trúc giải pháp
	- Mô hình MVC sẽ giúp tổ chức ứng dụng thành các thành phần rõ ràng, hỗ trợ mở rộng và bảo trì dễ dàng.

2. Thiết kế kiến trúc
- entities package:
	* BankTransaction: đại diện cho một giao dịch
- services package:
	* BankTransactionAnalysisService: chứa các phương thức phân tích dữ liệu.
- repositories package: cơ chế lưu trữ tạm thời các giao dịch để phân tích.
- dao package: chứa các đối tượng truy cập dữ liệu.


## Những khó khăn khi mở rộng ứng dụng
1. Hỗ trợ nhiều định dạng file: Hiện tại chỉ hỗ trợ file CSV được ngân hàng cung cấp, tuy nhiên, người dùng có thể cũng sẽ tự theo dõi chi tiêu cá nhân của mình trong những định dạng file khác nhau như excel,...
2. Hiệu suất: Xử lý lượng lớn giao dịch có thể chậm khi dữ liệu tăng.
3. Tính năng bổ sung: các yêu cầu mới từ người dùng như muốn có biểu đồ phân tích trực quan,... có thể làm tăng độ phức tạp.

## Giải pháp khắc phục khó khăn
1. Hỗ trợ nhiều định dạng file: cần có các phương thức để đọc các loại file các nhau tùy theo lựa chọn của người dùng. Có thể áp dụng Factory Pattern để tách biệt logic xử lý các dạng file khác nhau.
2. Cải thiện hiệu suất: cần phải có các cơ chế thống kê để hỗ trợ các thao tác phân tích sau không cần phải tính toán lại trên toàn bộ dữ liệu giao dịch. 
3. Để dễ dàng bổ sung các tính năng thì cần tổ chức project hiệu quả, tách các tính năng thành module riêng biệt để dễ dàng thêm mới hoặc sửa đổi.