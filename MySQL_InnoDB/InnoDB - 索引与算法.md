# InnoDB - 索引与算法

## 1、索引概述

**支持常见索引：**

* B+ 树索引
* 全文索引
* 哈希索引

*PS: B+树中的B不是代表二叉(binary), 而是代表平衡(balance), 因为B+ 树是从最早的平衡二叉树演变而来， 但是B+ 树不是一个二叉树。*

***

## 2、数据结构与算法

### 2.1  二分查找

* 又叫折半查找法；
* InnoDB引擎的表中，每页Page Directory 中的槽 是按照主键的顺序存放的，对于某一条具体记录的查询是通过对 Page Directory 进行二分查找得到的。

### 2.2  二叉查找树

* 左子树的键值小于根的键值
* 右子树的键值大于根的键值
* 不同的构造方式会影响查询效率

<img src="E:\cmder\A_CS_NOTES\CS_Notes\MySQL_InnoDB\二叉查找树(1).png" style="zoom:55%;" />  <img src="E:\cmder\A_CS_NOTES\CS_Notes\MySQL_InnoDB\二叉查找树(2).png" style="zoom:55%;" />

### 2.3  二叉平衡树  (AVL)

* 查询效率最高的二叉查找树，就是二叉平衡树：
  * 符合二叉树查找树定义；
  * 满足任何节点的两个子树的高度差为 1；
* 平衡二叉树查找速度快，效率高；
* 维持平衡二叉树的代价高，需要大量操作（但多用于内存中，所以实际消耗不大）；
* 需要1次及以上的左旋右旋来得到插入或更新后的平衡性；

<img src="E:\cmder\A_CS_NOTES\CS_Notes\MySQL_InnoDB\平衡二叉树的平衡操作.png" style="zoom:75%;" />

***

### 2.4 B+ 树

