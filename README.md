Splitwise

1. Add/remove users

2. Add group + add/remove users to a group

3. Add expenses between 2 users. (p2p expense)

4. Add group expenses.
 [Txn can be made within subset of people from group (ex: 10 people txn can be made within 2,3,4…..10 people]

5. Implement Equal split, keep code extensible for custom split.

6. Should be able to settle the balance between 2 users. (it should mark settlement in all groups too)
Ex:	user_1 owes user_2 Rs 100 as P2P
    User_1 owes user_2 Rs 120 in group_1
    User_2 owes user_1 Rs 130 in group_2
Net Balance: (100+120)-130 = 90
There is no partial settlement as an MVP product
	

Extension if time allows:
	Split unequally/custom one person pays and custom cost for each participant in a group.

**Expectation**:
	Write a clean code keeping in mind OOPS principle and extensibility of code.
	Use in memory data structures to keep track of records or use in-memory db if you are comfortable.
