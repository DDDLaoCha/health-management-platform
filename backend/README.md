# Health Management Platform - Backend

Spring Boot 3.x + Java 21 后端服务。

## 环境要求

- JDK 21+
- 无需单独安装 Maven（使用 Maven Wrapper）

## 启动项目

在 `backend` 目录下执行：

```bash
.\mvnw.cmd spring-boot:run
```

首次运行会自动下载 Maven 和项目依赖，需要联网。

## 验证服务

服务启动后默认监听 `http://localhost:8080`。

```bash
curl http://localhost:8080/api/health
```

预期返回：

```json
{"status":"ok"}
```

## 项目结构

```
backend/
├── .mvn/wrapper/          # Maven Wrapper 配置
├── src/main/java/com/health/platform/
│   ├── HealthManagementApplication.java   # 启动类
│   └── controller/
│       └── HealthController.java          # /api/health 接口
├── src/main/resources/
│   └── application.properties
├── mvnw                   # Unix 启动脚本
├── mvnw.cmd               # Windows 启动脚本
└── pom.xml
```
