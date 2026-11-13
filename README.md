# NotePad 记事本应用 — 期中实验

移动软件开发期中实验，基于 Android 原生技术实现一个 **NotePad 记事本应用**，
在笔记基础功能之上完成了题目要求的**时间戳显示**与**笔记查询**，并额外实现了
多项附加功能。

## 一、实验要求与完成情况

| 类型 | 要求 | 完成情况 |
| --- | --- | --- |
| 基本要求 | NoteList 界面笔记条目增加时间戳显示 | ✅ 每条笔记显示最后修改时间 |
| 基本要求 | 添加笔记查询功能（根据标题或内容查询） | ✅ 顶部搜索框实时过滤标题/内容 |
| 附加功能 | UI 美化（主题/背景/编辑器优化） | ✅ 自定义配色 + 深浅色主题切换 |
| 附加功能 | 笔记分类 | ✅ 内置分类 + 分类筛选 |
| 附加功能 | 笔记排序 | ✅ 按修改时间 / 标题排序 |
| 附加功能 | 笔记便签（置顶） | ✅ 笔记可置顶、置顶优先显示 |

> 附加功能共完成 **4 项**，满足「至少两项」的要求。

## 二、功能说明

### 基本功能
- **时间戳显示**：列表每条笔记右下角显示 `yyyy-MM-dd HH:mm` 格式的最后修改时间
  （`data/TimestampFormatter.kt`）。
- **笔记查询**：顶部搜索框对标题与内容进行**忽略大小写**的模糊匹配，输入即过滤
  （`data/NoteQuery.kt`）。

### 附加功能
1. **UI 美化**：自定义暖色纸质感配色方案，支持**深色 / 浅色**手动切换。
2. **笔记分类**：内置「工作 / 学习 / 生活 / 旅行 / 其他」分类，编辑时可选，
   列表页可一键筛选。
3. **笔记排序**：支持「修改时间 · 新→旧 / 旧→新」「标题 · A→Z / Z→A」四种排序。
4. **笔记便签（置顶）**：编辑/列表中可置顶笔记，置顶笔记始终排在最前。

## 三、技术架构

- **语言**：Kotlin
- **UI**：Jetpack Compose + Material 3
- **数据持久化**：SQLite（`SQLiteOpenHelper`），封装于 `SqliteNoteRepository`
- **响应式**：`StateFlow` + `collectAsState` 驱动 UI 自动刷新
- **分层**：数据层（`data/`）与界面层（`ui/`）分离，搜索/排序/过滤为纯函数，便于单测

## 四、项目结构

```
app/src/main/java/com/example/asqizhong/
├── MainActivity.kt                 # 入口，注入 SqliteNoteRepository
├── data/
│   ├── Note.kt                     # 笔记实体
│   ├── NoteCategory.kt             # 分类定义
│   ├── NoteSortOrder.kt            # 排序枚举与比较器
│   ├── NoteQuery.kt                # 搜索/筛选/排序纯函数
│   ├── TimestampFormatter.kt       # 时间戳格式化
│   ├── NoteRepository.kt           # 仓库接口
│   ├── InMemoryNoteRepository.kt   # 内存实现（供单元测试）
│   ├── NoteDatabaseHelper.kt       # SQLiteOpenHelper
│   └── SqliteNoteRepository.kt     # SQLite 实现
└── ui/
    ├── NotePadApp.kt               # 导航与主题状态
    ├── NoteListScreen.kt           # 笔记列表 + 搜索 + 分类 + 排序
    ├── NoteEditorScreen.kt         # 笔记编辑/新建
    └── theme/                      # 自定义配色主题
```

## 五、如何运行

1. 用 **Android Studio** 打开项目根目录（`asqizhong`）。
2. 等待 Gradle 同步完成（需联网下载依赖）。
3. 连接模拟器或真机（Android 7.0 / API 24 及以上）。
4. 点击 **Run ▶** 运行，或执行：

```bash
./gradlew installDebug
```

## 六、如何测试

### 单元测试（无需设备）

```bash
./gradlew testDebugUnitTest
```

测试结果位于 `app/build/reports/tests/testDebugUnitTest/index.html`。

共 **19 个单元测试，全部通过**，覆盖三部分：

| 测试类 | 数量 | 覆盖内容 |
| --- | --- | --- |
| `NoteQueryTest` | 11 | 空查询、标题/内容搜索、大小写、分类筛选、置顶优先、四种排序 |
| `InMemoryNoteRepositoryTest` | 6 | 新增、查询、更新、删除、置顶切换、异常查询 |
| `TimestampFormatterTest` | 2 | 时间戳格式化（UTC 固定值校验） |

### 构建 APK

```bash
./gradlew assembleDebug
```

产物：`app/build/outputs/apk/debug/app-debug.apk`

## 七、手动验证清单（Golden Path）

1. 启动应用 → 显示空列表「暂无笔记」。
2. 点击「＋ 新建笔记」→ 输入标题/内容、选择分类 → 保存。
3. 列表中出现该笔记，右下角显示当前时间。
4. 在搜索框输入标题或内容关键字 → 列表实时过滤。
5. 切换分类 chip → 只显示对应分类笔记。
6. 切换「排序」→ 列表按选择顺序重排。
7. 编辑笔记 → 置顶 → 返回列表，该笔记排在最前并显示「置顶」标签。
8. 点击右上角「深色/浅色」→ 主题即时切换。
