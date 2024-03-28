# Project - Bank Account


The bank account application will allow users to create accounts to
withdraw money, deposit money, and make bill payments.
Users can use the application to practise simple banking and managing money.
This project is of interest to me because
personal banking is a major aspect of university life as students
often need to make many bill payments.

## User Stories
- As a user, I want to be able to **deposit/withdraw money** to/from my accounts.
- As a user, I want to be able to **pay bills** and **receive payments** using my accounts.
- As a user, I want to be able to **create/delete** multiple accounts.
- As a user, I want to be able to **see the list** of my accounts.
- As a user, I want to be able to **see the details**
of my accounts (_date opened, expiry date, balance, etc._).
- As a user, I want to be able to **save** my accounts.
- As a user, I want to be able to **load** my accounts from file.

## Instructions for Grader
- You can create multiple accounts by pressing the create account button on screen
or in the file menu and typing a name.
- You can view account details by pressing the view all details button on screen
or in the file menu, or by selecting an account on screen and pressing the view details button.
- You can delete an account by selecting an account on screen and pressing the delete button.
- You can deposit/withdraw money by pressing the corresponding buttons on screen
after selecting an account.
- You can locate the visual component in the loading screen when the application starts.
- You can save the state of my application by pressing the save button on screen
or in the file menu
or pressing Ctrl+S.
- You can reload the state of my application by pressing the load button on screen
or in the file menu
or pressing Ctrl+L.

## Phase 4: Task 2
Wed Mar 27 19:40:38 PDT 2024
Account Manager application started

Wed Mar 27 19:40:42 PDT 2024
Account created: acc1

Wed Mar 27 19:40:47 PDT 2024
$30.0 deposited to: acc1

Wed Mar 27 19:40:50 PDT 2024
$20.0 withdrawn from: acc1

Wed Mar 27 19:40:53 PDT 2024
Total balance viewed: $10.0

Wed Mar 27 19:40:55 PDT 2024
Account renewed: acc1

Wed Mar 27 19:40:59 PDT 2024
Account created: acc2

Wed Mar 27 19:41:01 PDT 2024
Viewed accounts in account list

Wed Mar 27 19:41:01 PDT 2024
Viewed all account names

Wed Mar 27 19:41:03 PDT 2024
Viewed accounts in account list

Wed Mar 27 19:41:03 PDT 2024
Viewed all account details

Wed Mar 27 19:41:06 PDT 2024
Viewed account details for: acc1

Wed Mar 27 19:41:10 PDT 2024
Account deleted: acc1

Wed Mar 27 19:41:12 PDT 2024
Account deleted: acc2

## Phase 4: Task 3

One example of refactoring that could be done is in
the AccountManagerGui class in the ui package.
This class has another helper class, TextPrinter,
which might be better off extracted as an individual
class by itself due to the Single Responsibility Principle.
A good example of this is the LoadingScreen class,
which is a separate class that handles the loading screen
while the AccountManagerGui handles the main application screen.
There are also minor code quality issues in general
that can be fixed in general, such as changing if-else
clauses with empty if bodies to simple if clauses with the
condition inverted. These would be done to allow for
better code readability and less congestion of code
(i.e. less lines of code).
