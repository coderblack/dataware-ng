
-- 购物车记录表
drop table if exists ods_b2c_cart;
create table ods_b2c_cart(
id                      bigint            ,-- ID
session_id            string           ,-- 会话id
user_id                bigint           ,-- 用户id
goods_id               bigint           ,-- 商品ID
number                bigint            ,-- 商品数量
add_time              string            ,-- 加购时间
cancel_time           string           ,-- 商品取消时间
submit_time           string           ,-- 商品提交时间
dw_date               string            -- 数仓计算日期
);

-- 商品信息表
drop table if exists dwd_b2c_goods;
create table dwd_b2c_goods(
sku_id                 bigint  ,--SKU编号  
sku_name               string  ,--SKU名称  小米8少女粉128G尊享版
spu_id                 bigint  ,--商品编号
spu_no                 string  ,--商品货号
spu_sn                 string  ,--商品条码
spu_name               string  ,--商品名称  小米8
size_id                bigint  ,--尺码编号
size_name              string  ,--尺码名称
colour_id              bigint  ,--颜色ID
shop_id                bigint  ,--店铺编号
shop_name              string  ,--店铺名称
curr_price             double  ,--售卖价格
market_price           double  ,--市场价格
discount               double  ,--折扣比例
cost_price             double  ,--成本价格
cost_type              string  ,--成本类型
warehouse              string  ,--所在仓库
stock_cnt              bigint  ,--进货数量
stock_amt              double  ,--进货货值
first_cat              bigint  ,--1级分类ID
first_cat_name         string  ,--1级分类名称
second_cat             bigint  ,--2级分类ID
second_cat_name        string  ,--2级分类名称
third_cat              bigint  ,--3级分类ID
third_cat_name         string  ,--3级分类名称
brand_id                string  ,-- 品牌id
brand_name              string , --品牌名称
dw_date                string  
);


-- 订单主要信息   ods_b2c_orders
drop table if exists ods_b2c_orders;
create table ods_b2c_orders(
order_id                 bigint       ,--订单ID
order_no                 string       ,--订单号
order_date               timestamp    ,--订单日期
user_id                  bigint       ,--用户ID
user_name                string       ,--登录名
order_money              double       ,--订单金额(应付金额)
order_type               string       ,--订单类型    0->正常订单；1->秒杀订单
--订单状态： 0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单；6->退货；7-》拒收
order_status             string       , 
-- 支付类型：1->支付宝；2->微信
pay_type                 string       ,
-- 支付状态：0->未支付；1->支付已完成 ； 2->支付失败
pay_status               string       ,
-- 0->PC订单；1->app订单 ; 2->微信订单
order_source             string       ,--订单来源

last_update_time         timestamp    ,--订单最后修改时间
dw_date                  timestamp    
)
partitioned by
(dt string)
;


-- 订单商品详情表
drop table if exists ods_b2c_orders_goods;
create table ods_b2c_orders_goods(
order_id              bigint     ,-- 订单ID
goods_id              bigint     ,-- 商品ID
cat_id                bigint     ,-- 类目ID
cat_name              string     ,-- 3级类目名称
size_id               bigint     ,-- 条码ID
goods_price           double     ,-- 订单商品价格
goods_amount          bigint     ,-- 数量
create_time           string     ,-- 创建日期
last_update_time      string     ,-- 最后修改日期
dw_date               string  
)
;

-- 订单收货人信息  dwd_b2c_orders_desc
drop table if exists dwd_b2c_orders_desc;
create table dwd_b2c_orders_desc(
order_id                 bigint       ,-- 订单ID 
order_no                 string       ,-- 订单号
consignee                string       ,-- 收货人姓名
area_id                  bigint       ,-- 收货人地址ID
area_name                string       ,-- 地址ID对应地址段（粒度到县区，有些做得好的做到镇）
address                  string       ,-- 收货人地址（收货人手工填的地址）
mobilephone              string       ,-- 收货人手机号
telephone                 string       ,-- 收货人电话
coupon_id                bigint       ,-- 使用代金券ID
coupon_money             double       ,-- 使用代金券金额
carriage_money           double       ,-- 运费
create_time              string       ,-- 创建时间
last_update_time         string       ,-- 最后修改时间
dw_date                  string       
)
;





