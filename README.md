<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Tours and Travel Management System</title>
  <link href="https://fonts.googleapis.com/css2?family=Syne:wght@400;600;700;800&family=DM+Sans:ital,wght@0,300;0,400;0,500;1,300&display=swap" rel="stylesheet"/>
  <style>
    :root {
      --bg: #0d1117;
      --surface: #161b22;
      --surface2: #1c2330;
      --border: #30363d;
      --accent: #3fb950;
      --accent2: #58a6ff;
      --accent3: #d2a8ff;
      --text: #e6edf3;
      --muted: #8b949e;
      --danger: #f85149;
      --warn: #e3b341;
    }

    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      background: var(--bg);
      color: var(--text);
      font-family: 'DM Sans', sans-serif;
      font-size: 18px;
      line-height: 1.75;
      padding: 0 1rem 4rem;
    }

    .container {
      max-width: 900px;
      margin: 0 auto;
    }

    /* HEADER */
    header {
      padding: 4rem 0 2.5rem;
      border-bottom: 1px solid var(--border);
      margin-bottom: 3rem;
    }

    header h1 {
      font-family: 'Syne', sans-serif;
      font-size: 3rem;
      font-weight: 800;
      line-height: 1.15;
      background: linear-gradient(135deg, var(--accent) 0%, var(--accent2) 60%, var(--accent3) 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 1.2rem;
    }

    .badges {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;
      margin-bottom: 1.5rem;
    }

    .badge {
      display: inline-flex;
      align-items: center;
      gap: 0.35rem;
      font-family: 'Syne', sans-serif;
      font-size: 0.75rem;
      font-weight: 600;
      letter-spacing: 0.04em;
      text-transform: uppercase;
      padding: 0.3rem 0.75rem;
      border-radius: 999px;
      border: 1px solid;
    }

    .badge-green  { color: var(--accent);  border-color: var(--accent);  background: rgba(63,185,80,0.08); }
    .badge-blue   { color: var(--accent2); border-color: var(--accent2); background: rgba(88,166,255,0.08); }
    .badge-purple { color: var(--accent3); border-color: var(--accent3); background: rgba(210,168,255,0.08); }
    .badge-orange { color: var(--warn);    border-color: var(--warn);    background: rgba(227,179,65,0.08); }
    .badge-red    { color: var(--danger);  border-color: var(--danger);  background: rgba(248,81,73,0.08); }

    .preview-link {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      color: var(--accent2);
      text-decoration: none;
      font-size: 1rem;
      font-weight: 500;
      border: 1px solid var(--accent2);
      padding: 0.5rem 1.25rem;
      border-radius: 6px;
      transition: background 0.2s;
    }
    .preview-link:hover { background: rgba(88,166,255,0.1); }

    /* SECTIONS */
    section {
      margin-bottom: 3rem;
    }

    h2 {
      font-family: 'Syne', sans-serif;
      font-size: 1.6rem;
      font-weight: 700;
      color: var(--text);
      margin-bottom: 1.2rem;
      padding-bottom: 0.5rem;
      border-bottom: 1px solid var(--border);
      display: flex;
      align-items: center;
      gap: 0.6rem;
    }

    h2 .icon {
      font-size: 1.2rem;
      opacity: 0.8;
    }

    h3 {
      font-family: 'Syne', sans-serif;
      font-size: 1.15rem;
      font-weight: 600;
      color: var(--accent2);
      margin: 1.5rem 0 0.6rem;
    }

    p {
      color: #c9d1d9;
      font-size: 1.05rem;
      margin-bottom: 0.75rem;
    }

    /* TABLES */
    .table-wrap { overflow-x: auto; border-radius: 8px; border: 1px solid var(--border); margin-bottom: 1rem; }

    table {
      width: 100%;
      border-collapse: collapse;
      font-size: 1rem;
    }

    thead th {
      background: var(--surface2);
      color: var(--muted);
      font-family: 'Syne', sans-serif;
      font-size: 0.8rem;
      font-weight: 600;
      letter-spacing: 0.08em;
      text-transform: uppercase;
      padding: 0.85rem 1.1rem;
      text-align: left;
    }

    tbody td {
      padding: 0.85rem 1.1rem;
      border-top: 1px solid var(--border);
      color: #c9d1d9;
      vertical-align: top;
    }

    tbody tr:hover td { background: var(--surface2); }

    td strong, td code {
      color: var(--text);
    }

    /* FEATURE CARDS */
    .feature-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1rem;
    }

    @media (max-width: 600px) { .feature-grid { grid-template-columns: 1fr; } }

    .feature-card {
      background: var(--surface);
      border: 1px solid var(--border);
      border-radius: 10px;
      padding: 1.4rem 1.5rem;
    }

    .feature-card h3 {
      margin-top: 0;
      margin-bottom: 0.8rem;
      font-size: 1.05rem;
    }

    .feature-card ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .feature-card ul li {
      font-size: 1rem;
      color: #c9d1d9;
      padding: 0.25rem 0;
      display: flex;
      align-items: flex-start;
      gap: 0.55rem;
    }

    .feature-card ul li::before {
      content: "▸";
      color: var(--accent);
      flex-shrink: 0;
      margin-top: 0.05rem;
    }

    /* CODE BLOCKS */
    pre {
      background: var(--surface);
      border: 1px solid var(--border);
      border-radius: 8px;
      padding: 1.2rem 1.4rem;
      overflow-x: auto;
      font-size: 0.92rem;
      line-height: 1.65;
      margin: 0.75rem 0 1rem;
    }

    code {
      font-family: 'JetBrains Mono', 'Fira Code', 'Cascadia Code', monospace;
      font-size: 0.9rem;
      color: var(--accent3);
    }

    pre code {
      color: #c9d1d9;
    }

    .code-comment { color: var(--muted); }
    .code-key     { color: var(--accent2); }
    .code-val     { color: var(--accent); }

    /* STEPS / FLOW */
    .flow {
      display: flex;
      flex-direction: column;
      gap: 0;
      margin: 1rem 0;
    }

    .flow-step {
      display: flex;
      align-items: flex-start;
      gap: 1rem;
    }

    .flow-line {
      display: flex;
      flex-direction: column;
      align-items: center;
      flex-shrink: 0;
      width: 28px;
    }

    .flow-dot {
      width: 12px;
      height: 12px;
      border-radius: 50%;
      background: var(--accent);
      border: 2px solid var(--bg);
      box-shadow: 0 0 0 2px var(--accent);
      flex-shrink: 0;
      margin-top: 0.45rem;
    }

    .flow-connector {
      width: 2px;
      flex: 1;
      min-height: 28px;
      background: linear-gradient(to bottom, var(--accent), var(--accent2));
      opacity: 0.4;
    }

    .flow-step:last-child .flow-connector { display: none; }

    .flow-text {
      font-size: 1rem;
      color: #c9d1d9;
      padding: 0.3rem 0 1rem;
    }

    /* CHECKLIST */
    .checklist {
      list-style: none;
      padding: 0;
      display: flex;
      flex-direction: column;
      gap: 0.4rem;
    }

    .checklist li {
      display: flex;
      align-items: center;
      gap: 0.75rem;
      font-size: 1rem;
      color: #c9d1d9;
      padding: 0.5rem 0.9rem;
      border-radius: 6px;
      background: var(--surface);
      border: 1px solid var(--border);
    }

    .checklist .check-icon {
      width: 18px;
      height: 18px;
      border-radius: 4px;
      border: 1.5px solid var(--border);
      flex-shrink: 0;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 0.75rem;
      color: var(--muted);
    }

    /* INLINE CODE */
    p code, li code, td code {
      background: var(--surface2);
      padding: 0.15em 0.4em;
      border-radius: 4px;
      font-size: 0.88em;
      color: var(--accent3);
      border: 1px solid var(--border);
    }

    /* RECENT UPDATES */
    .update-list {
      list-style: none;
      padding: 0;
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }

    .update-list li {
      display: flex;
      align-items: flex-start;
      gap: 0.75rem;
      font-size: 1rem;
      color: #c9d1d9;
    }

    .update-list li::before {
      content: "✦";
      color: var(--accent);
      flex-shrink: 0;
      font-size: 0.8rem;
      margin-top: 0.35rem;
    }

    /* FOOTER */
    footer {
      margin-top: 4rem;
      padding-top: 2rem;
      border-top: 1px solid var(--border);
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: gap;
      gap: 1rem;
    }

    footer .author {
      font-family: 'Syne', sans-serif;
      font-size: 1.05rem;
      font-weight: 600;
    }

    footer a {
      color: var(--accent2);
      text-decoration: none;
    }

    footer a:hover { text-decoration: underline; }

    footer .license {
      font-size: 0.9rem;
      color: var(--muted);
    }

    /* DIVIDER */
    hr {
      border: none;
      border-top: 1px solid var(--border);
      margin: 2rem 0;
    }

    a { color: var(--accent2); }

    /* Structure tree */
    .tree {
      font-family: 'JetBrains Mono', monospace;
      font-size: 0.9rem;
      color: #c9d1d9;
      background: var(--surface);
      border: 1px solid var(--border);
      border-radius: 8px;
      padding: 1.2rem 1.4rem;
      line-height: 1.9;
    }

    .tree .dir  { color: var(--accent2); }
    .tree .file { color: var(--muted); }
    .tree .hl   { color: var(--accent3); }
  </style>
</head>
<body>
<div class="container">

  <!-- HEADER -->
  <header>
    <h1>Tours &amp; Travel<br>Management System</h1>
    <div class="badges">
      <span class="badge badge-orange">Java 21</span>
      <span class="badge badge-green">Spring Boot 3</span>
      <span class="badge badge-blue">Spring Security</span>
      <span class="badge badge-blue">H2 · MySQL</span>
      <span class="badge badge-red">Maven</span>
      <span class="badge badge-purple">Razorpay</span>
      <span class="badge badge-orange">SSR · Thymeleaf</span>
    </div>
    <a class="preview-link" href="https://tours-and-travel-management-system.netlify.app" target="_blank">
      🌐 Live Frontend Preview
    </a>
  </header>

  <!-- ABOUT -->
  <section>
    <h2><span class="icon">📖</span> About</h2>
    <p>
      The <strong>Tours and Travel Management System</strong> is a full-stack travel booking web application built
      with <strong>Spring Boot, Spring Security, Razorpay, JPA/Hibernate, and Thymeleaf</strong>.
    </p>
    <p>
      Users can explore travel packages, make bookings, and complete secure payments. Administrators manage packages,
      users, bookings, and payments through a dedicated dashboard.
    </p>
  </section>

  <!-- TECH STACK -->
  <section>
    <h2><span class="icon">🛠</span> Tech Stack</h2>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Layer</th><th>Technologies</th></tr></thead>
        <tbody>
          <tr><td><strong>Backend</strong></td><td>Java 21, Spring Boot 3, Spring Security, Spring Data JPA, Hibernate</td></tr>
          <tr><td><strong>Frontend</strong></td><td>Thymeleaf, HTML5, CSS3, JavaScript</td></tr>
          <tr><td><strong>Database</strong></td><td>H2 (development), MySQL (production)</td></tr>
          <tr><td><strong>Payment</strong></td><td>Razorpay Payment Gateway</td></tr>
          <tr><td><strong>Build</strong></td><td>Maven</td></tr>
          <tr><td><strong>Tooling</strong></td><td>Lombok, Spring Boot DevTools, IntelliJ IDEA / Eclipse</td></tr>
        </tbody>
      </table>
    </div>
  </section>

  <!-- FEATURES -->
  <section>
    <h2><span class="icon">✨</span> Features</h2>
    <div class="feature-grid">
      <div class="feature-card">
        <h3>👤 User</h3>
        <ul>
          <li>Register and log in securely</li>
          <li>Browse and view travel packages</li>
          <li>Book packages online</li>
          <li>Pay via Razorpay</li>
          <li>Track booking status</li>
        </ul>
      </div>
      <div class="feature-card">
        <h3>🔧 Admin</h3>
        <ul>
          <li>Manage travel packages</li>
          <li>Manage users and bookings</li>
          <li>Monitor payments</li>
          <li>Role-based access control</li>
          <li>Admin dashboard</li>
        </ul>
      </div>
    </div>
  </section>

  <!-- ARCHITECTURE -->
  <section>
    <h2><span class="icon">🏗</span> Architecture</h2>
    <p>MVC with Server-Side Rendering (SSR) via Thymeleaf.</p>
    <div class="flow">
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Client Request (Browser)</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Spring Boot Controller</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Service Layer (Business Logic)</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Repository Layer (Spring Data JPA)</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Database (H2 / MySQL)</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div></div><div class="flow-text">Thymeleaf renders HTML → returned to browser</div></div>
    </div>
  </section>

  <!-- PAYMENT FLOW -->
  <section>
    <h2><span class="icon">💳</span> Payment Flow</h2>
    <div class="flow">
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">User selects travel package</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">App creates Razorpay order</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">User completes payment</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div><div class="flow-connector"></div></div><div class="flow-text">Backend verifies payment</div></div>
      <div class="flow-step"><div class="flow-line"><div class="flow-dot"></div></div><div class="flow-text">Booking confirmed and saved to database</div></div>
    </div>
  </section>

  <!-- PROJECT STRUCTURE -->
  <section>
    <h2><span class="icon">📁</span> Project Structure</h2>
    <div class="tree">
<span class="dir">src/main</span>
├── <span class="dir">java/com/Aj</span>
│   ├── <span class="hl">Controller</span>
│   ├── <span class="hl">Service</span>
│   ├── <span class="hl">Repository</span>
│   ├── <span class="hl">Entity</span>
│   ├── <span class="hl">DTO</span>
│   ├── <span class="hl">SecurityConfiguration</span>
│   └── <span class="hl">Exception</span>
└── <span class="dir">resources</span>
    ├── <span class="dir">static</span>
    │   ├── <span class="file">css/</span>
    │   ├── <span class="file">js/</span>
    │   └── <span class="file">images/</span>
    ├── <span class="dir">templates</span>
    │   ├── <span class="file">Admin/</span>
    │   └── <span class="file">User/</span>
    └── <span class="file">application.properties</span>
    </div>
  </section>

  <!-- GETTING STARTED -->
  <section>
    <h2><span class="icon">🚀</span> Getting Started</h2>

    <h3>Prerequisites</h3>
    <ul class="update-list">
      <li>Java 21+</li>
      <li>Maven</li>
      <li>MySQL (optional — H2 is used by default)</li>
      <li>IntelliJ IDEA or Eclipse</li>
    </ul>

    <h3>Clone and Run</h3>
    <pre><code>git clone https://github.com/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM.git
cd TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM
mvn spring-boot:run</code></pre>
    <p>App runs at: <code>http://localhost:8081</code></p>

    <h3>H2 Console (Development)</h3>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Setting</th><th>Value</th></tr></thead>
        <tbody>
          <tr><td>URL</td><td><code>http://localhost:8081/h2-console</code></td></tr>
          <tr><td>JDBC URL</td><td><code>jdbc:h2:mem:testdb</code></td></tr>
          <tr><td>Username</td><td><code>sa</code></td></tr>
          <tr><td>Password</td><td><em style="color:var(--muted)">leave blank</em></td></tr>
        </tbody>
      </table>
    </div>
  </section>

  <!-- RECENT UPDATES -->
  <section>
    <h2><span class="icon">🔄</span> Recent Updates</h2>
    <ul class="update-list">
      <li>Fixed Spring Security configuration issues</li>
      <li>Resolved runtime startup problems</li>
      <li>Updated dependencies for Spring Boot 3</li>
      <li>Improved project architecture (DTO, Service, Repository)</li>
      <li>Enhanced payment processing flow</li>
      <li>Added exception handling</li>
    </ul>
  </section>

  <!-- ROADMAP -->
  <section>
    <h2><span class="icon">🗺</span> Roadmap</h2>
    <ul class="checklist">
      <li><span class="check-icon">○</span> REST API support</li>
      <li><span class="check-icon">○</span> Docker containerization</li>
      <li><span class="check-icon">○</span> Pagination and sorting</li>
      <li><span class="check-icon">○</span> Email notifications for booking confirmation</li>
      <li><span class="check-icon">○</span> Swagger API documentation</li>
      <li><span class="check-icon">○</span> Cloud deployment</li>
    </ul>
  </section>

  <!-- ACADEMIC -->
  <section>
    <h2><span class="icon">🎓</span> Academic Certification</h2>
    <p>
      Developed as part of the <strong>Master of Computer Applications (MCA)</strong> curriculum.
      Supported by a project report, college certification, and project approval documentation.
    </p>
  </section>

  <!-- FOOTER -->
  <footer>
    <div>
      <div class="author">Abinash Nayak</div>
      <div style="color:var(--muted); font-size:0.95rem;">Java Backend Developer · MCA Graduate</div>
      <a href="https://github.com/Aj-world" target="_blank">github.com/Aj-world</a>
    </div>
    <div class="license">Educational &amp; Learning Purposes</div>
  </footer>

</div>
</body>
</html>
