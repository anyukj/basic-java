# xxx代码规范说明

### 技术栈
  - 组件
    - spring boot
    - spring security
    - mybatis plus
    - druid
    - hibernate-validator
    - fastjson
    - swagger
  - 基础服务
    - mysql
    - redis

### 模块说明
  - biz 自身业务服务，启动类也在这
  - config 配置管理
  - core 公共核心组件封装
  - utils 工具包
  
### 分支说明
  - dev 开发分支也是测试分支
  - prod 生产分支
  
### validator使用规约
  - 基本类型的包装类，空校验统一使用@NotNull
  - String类型空校验统一使用@NotBlank
  - 集合类型空校验统一使用@NotEmpty 

### 部分规约
  - dubbo接口定义
    - 统一响应OneCodeServiceResponse
    - 入参需实体化，禁止使用基础数据类型
  - 分层领域模型规约
    - DTO (Data Transfer Object)：数据传输对象，API 层接口定义业务数据输出对象。
    - VO (Value Object)：请求入参。API层接口定义的业务入参。
    - DO（Data Object）：与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
    - 其他各moudule内部可直接使用原始的pojo命名不必强加后缀，造成多次convert

### 数据库表设计规约
   - 数据表命名
      - 业务数据表统一TB_B_开头，如TB_B_TRADE
      - 资料表统一TB_F_开头, 如TB_F_USER
      - 业务基础数据配置表统一TD_B_开头, 如TD_B_CITY
      - 系统参数配置表统一TD_S_开头, 如TD_S_PARAMETER
      - 日志表统一TB_L_开头，如TB_L_COLOR
      - 中间表统一TB_M_开头，如TB_M_LOCATION_PATIENT
    - 表结构设计
      - 资料表、业务数据表需要统一定义以下字段
        - id
        - create_time
        - update_time
        - version
        - updator
        - disable 逻辑删除标志
      - 字段类型选择注意事项
        - 用于统计是否的字段统一使用tinyint(1)
        - 禁止使用varchar定义日期类型,没有特殊需求尽量使用timestamp
        - 高查询需求的数据表禁止定义大字段
      - 索引设计
        - 尽量在开发前理清业务需求查询场景，按需合理设置索引，不要过多添加索引
