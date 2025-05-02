💰 Finance Tracker CLI

📝 Description

The Finance Tracker CLI is a Java-based command-line application designed to help users manage and track their personal finances through a simple text interface. Users can record deposits and payments, view a detailed ledger, and generate various financial reports such as Month-to-Date, Year-to-Date, and Vendor-specific summaries.

Transactions are stored in a CSV file using the format:

YYYY-MM-DD|HH:MM:SS|Description|Vendor|Amount
The application supports full CRUD-like behavior for adding and viewing financial records and is built with file I/O handling, date/time filtering, and formatted terminal output.

✨ Interesting Code Snippet

The viewLedger() method stands out for its flexible filtering and formatted output, allowing users to switch between views (all, deposits, payments) and reversing the transaction list for readability.

Finance Tracker Menu
<img width="1397" alt="Screenshot 2025-05-02 at 1 01 58 AM" src="https://github.com/user-attachments/assets/da89ad9a-04fa-4477-9968-b2adf8fc80e4" />

Adding payment 
<img width="1394" alt="Screenshot 2025-05-02 at 1 04 41 AM" src="https://github.com/user-attachments/assets/e56df4c5-78b9-4011-acf3-3ba8ec502a09" />

Ledger Option
<img width="1440" alt="Screenshot 2025-05-02 at 1 05 23 AM" src="https://github.com/user-attachments/assets/42d21bd3-d6c9-4b21-abc2-6a8d7d4198cc" />
