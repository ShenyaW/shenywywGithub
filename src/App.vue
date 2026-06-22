<script setup>
import { computed, ref } from "vue";

const summary = ref({
  income: 18500,
  expense: 7820,
});

const categories = ref([
  { name: "餐饮", amount: 2190, color: "#16a06b" },
  { name: "住房", amount: 1800, color: "#3278d8" },
  { name: "交通", amount: 1480, color: "#d99424" },
  { name: "购物", amount: 1250, color: "#1a9fb5" },
  { name: "娱乐", amount: 1100, color: "#7b61d1" },
]);

const transactions = ref([
  {
    title: "工资到账",
    date: "6月1日 09:12",
    amount: 18500,
    type: "income",
    icon: "薪",
  },
  {
    title: "超市采购",
    date: "5月31日 18:40",
    amount: -326,
    type: "expense",
    icon: "购",
  },
  {
    title: "地铁通勤",
    date: "5月31日 08:10",
    amount: -14,
    type: "expense",
    icon: "行",
  },
  {
    title: "房租",
    date: "5月30日 10:00",
    amount: -1800,
    type: "expense",
    icon: "住",
  },
  {
    title: "咖啡与简餐",
    date: "5月29日 13:24",
    amount: -68,
    type: "expense",
    icon: "餐",
  },
]);

const showAllTransactions = ref(false);
const actionMessage = ref("可以记录一笔示例支出，或展开查看全部明细。");

const formatCurrency = (value) =>
  new Intl.NumberFormat("zh-CN", {
    style: "currency",
    currency: "CNY",
    maximumFractionDigits: 0,
  }).format(value);

const totalExpense = computed(() =>
  categories.value.reduce((sum, item) => sum + item.amount, 0),
);

const balance = computed(() => summary.value.income - summary.value.expense);

const categorySegments = computed(() => {
  let cursor = 0;

  return categories.value
    .map((item, index) => {
      const isLast = index === categories.value.length - 1;
      const percent = isLast
        ? 100 - cursor
        : Math.round((item.amount / totalExpense.value) * 100);
      const segment = `${item.color} ${cursor}% ${cursor + percent}%`;
      cursor += percent;

      return segment;
    })
    .join(", ");
});

const enrichedCategories = computed(() =>
  categories.value.map((item) => ({
    ...item,
    percent: Math.round((item.amount / totalExpense.value) * 100),
  })),
);

const statCards = computed(() => [
  {
    label: "收入",
    value: formatCurrency(summary.value.income),
    note: "较上月 +12.4%",
    tone: "income",
  },
  {
    label: "支出",
    value: formatCurrency(summary.value.expense),
    note: "预算使用 68%",
    tone: "expense",
  },
  {
    label: "余额",
    value: formatCurrency(balance.value),
    note: "可支配资金",
    tone: "balance",
  },
]);

const visibleTransactions = computed(() =>
  showAllTransactions.value ? transactions.value : transactions.value.slice(0, 3),
);

const addSampleTransaction = () => {
  const amount = 48;
  const newTransaction = {
    title: "新增示例支出",
    date: "刚刚",
    amount: -amount,
    type: "expense",
    icon: "记",
  };

  transactions.value.unshift(newTransaction);
  summary.value.expense += amount;
  categories.value[0].amount += amount;
  actionMessage.value = `已记录一笔 ${formatCurrency(amount)} 的餐饮支出。`;
};

const toggleTransactions = () => {
  showAllTransactions.value = !showAllTransactions.value;
  actionMessage.value = showAllTransactions.value
    ? "已展开全部交易明细。"
    : "已收起为最近三笔交易。";
};
</script>

<template>
  <main class="dashboard">
    <section class="hero" aria-label="账户概览">
      <div>
        <p class="eyebrow">六月账本</p>
        <h1>个人记账仪表盘</h1>
        <p class="summary">清晰查看本月现金流、消费分类和最近交易。</p>
      </div>
      <button class="primary-button" type="button" @click="addSampleTransaction">
        + 记录一笔
      </button>
    </section>

    <p class="action-message" role="status">{{ actionMessage }}</p>

    <section class="stats-grid" aria-label="财务统计">
      <article
        v-for="card in statCards"
        :key="card.label"
        class="stat-card"
        :class="card.tone"
      >
        <span>{{ card.label }}</span>
        <strong>{{ card.value }}</strong>
        <small>{{ card.note }}</small>
      </article>
    </section>

    <section class="content-grid">
      <article class="panel chart-panel">
        <div class="panel-header">
          <div>
            <p class="eyebrow">分类占比</p>
            <h2>本月支出结构</h2>
          </div>
          <span class="pill">实时</span>
        </div>

        <div class="chart-wrap">
          <div
            class="donut"
            :style="{ background: `conic-gradient(${categorySegments})` }"
            aria-hidden="true"
          ></div>
          <div class="chart-center">
            <span>总支出</span>
            <strong>{{ formatCurrency(totalExpense) }}</strong>
          </div>
        </div>

        <ul class="category-list">
          <li
            v-for="item in enrichedCategories"
            :key="item.name"
            class="category-row"
          >
            <div class="category-main">
              <span class="dot" :style="{ backgroundColor: item.color }"></span>
              <span class="category-meta">
                <span class="category-name">{{ item.name }}</span>
                <span class="category-percent">{{ item.percent }}%</span>
              </span>
            </div>
            <strong>{{ formatCurrency(item.amount) }}</strong>
          </li>
        </ul>
      </article>

      <article class="panel transactions-panel">
        <div class="panel-header">
          <div>
            <p class="eyebrow">最近交易</p>
            <h2>收支明细</h2>
          </div>
          <button class="ghost-button" type="button" @click="toggleTransactions">
            {{ showAllTransactions ? "收起" : "查看全部" }}
          </button>
        </div>

        <div class="transactions">
          <div
            v-for="item in visibleTransactions"
            :key="`${item.title}-${item.date}-${item.amount}`"
            class="transaction"
          >
            <span class="transaction-icon">{{ item.icon }}</span>
            <span class="transaction-meta">
              <span class="transaction-title">{{ item.title }}</span>
              <span class="transaction-date">{{ item.date }}</span>
            </span>
            <span
              class="transaction-amount"
              :class="item.type === 'income' ? 'income-text' : 'expense-text'"
            >
              {{ item.amount > 0 ? "+" : "" }}{{ formatCurrency(item.amount) }}
            </span>
          </div>
        </div>
      </article>
    </section>
  </main>
</template>
