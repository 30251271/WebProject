// SPA 라우팅 처리
const routes = {
  "#/home": "_______",
  "#/about": "_______",
  "#/board": "_______"
};

function showSection(route) {
  for (const id of Object.values(routes)) {
    document.getElementById(id).classList.remove("active");
  }

  const sectionId = routes[route] || "_______";
  document.getElementById(sectionId).classList.add("active");

  if (route === "#/_____") {
    renderPosts();
  }
}

window.addEventListener("load", () => {
  showSection(location.hash || "#/____");
});

window.addEventListener("hashchange", () => {
  showSection(location.hash);
});

// 게시판 기능
const form = document.getElementById("_______");
const input = document.getElementById("_______");
const list = document.getElementById("_______");

async function renderPosts() {
  try {
    const res = await fetch("___파일이름____"); // 클라이언트에서 직접 파일 로딩
    const text = await res.text();
    const lines = text.trim().split("\n").filter(Boolean);

    list.innerHTML = "";
    lines.forEach(line => {
      const li = document.createElement("li");
      li.textContent = line;
      list.appendChild(li);
    });
  } catch (err) {
    console.error("게시글 불러오기 실패:", err);
  }
}

if (form) {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const content = input.value.trim();
    if (!content) return;

    try {
      await fetch("/post", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        body: content
      });

      input.value = "";
      renderPosts();
    } catch (err) {
      console.error("게시글 작성 실패:", err);
    }
  });
}
