<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Журнал посещений</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #0d1117;
            color: #c9d1d9;
            min-height: 100vh;
            padding: 40px 20px;
        }
        .header { text-align: center; margin-bottom: 40px; }
        .header .icon { font-size: 60px; margin-bottom: 12px; }
        h1 { font-size: 2rem; color: #58a6ff; margin-bottom: 8px; }
        .card {
            max-width: 1100px;
            margin: 0 auto;
            background: #161b22;
            border: 1px solid #30363d;
            border-radius: 12px;
            overflow: hidden;
        }
        .card-header {
            padding: 20px 24px;
            border-bottom: 1px solid #30363d;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .card-header h2 { font-size: 1rem; color: #e6edf3; font-weight: 600; }
        .count-badge {
            background: #21262d;
            border: 1px solid #30363d;
            color: #8b949e;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 0.8rem;
        }
        table { width: 100%; border-collapse: collapse; }
        thead th {
            padding: 12px 20px;
            text-align: left;
            font-size: 0.78rem;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #8b949e;
            background: #0d1117;
            border-bottom: 1px solid #30363d;
        }
        tbody tr { border-bottom: 1px solid #21262d; transition: background 0.15s; }
        tbody tr:last-child { border-bottom: none; }
        tbody tr:first-child { background: rgba(88,166,255,0.05); }
        tbody tr:hover { background: #1c2128; }
        td { padding: 13px 20px; font-size: 0.88rem; vertical-align: top; }
        td.id-col { color: #8b949e; font-family: monospace; width: 60px; }
        td.time-col { color: #e6edf3; white-space: nowrap; width: 200px; font-family: monospace; }
        td.ua-col {
            color: #c9d1d9;
            word-break: break-all;
            font-family: 'Courier New', monospace;
            font-size: 0.8rem;
            line-height: 1.5;
        }
        .empty { padding: 40px; text-align: center; color: #8b949e; }
    </style>
</head>
<body>
<div class="header">
    <div class="icon">🗃️</div>
    <h1>Журнал посещений</h1>
</div>

<div class="card">
    <div class="card-header">
        <h2>📋 История запросов</h2>
        <span class="count-badge">${visits?size} записей</span>
    </div>

    <#if visits?has_content>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Дата и время</th>
                    <th>User-Agent</th>
                </tr>
            </thead>
            <tbody>
                <#list visits as visit>
                <tr>
                    <td class="id-col">${visit.id}</td>
                    <td class="time-col">${visit.formattedTime}</td>
                    <td class="ua-col">${visit.userAgent!"-"}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    <#else>
        <div class="empty">Записей пока нет</div>
    </#if>
</div>
</body>
</html>
