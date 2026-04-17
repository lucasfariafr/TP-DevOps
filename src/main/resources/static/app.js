const display = document.getElementById('display');
let currentValue = '0';
let previousValue = null;
let operator = null;
let waitingForOperand = false;

function formatDisplay(val) {
    const num = parseFloat(val);
    if (isNaN(num)) return val;
    return num.toString().replace('.', ',');
}

function updateDisplay() {
    display.textContent = formatDisplay(currentValue);
    display.classList.toggle('small', display.textContent.length > 8);
}

function inputDigit(digit) {
    if (waitingForOperand) {
        currentValue = digit;
        waitingForOperand = false;
    } else {
        currentValue = currentValue === '0' ? digit : currentValue + digit;
    }
    clearOperatorHighlight();
    updateDisplay();
}

function inputDecimal() {
    if (waitingForOperand) {
        currentValue = '0.';
        waitingForOperand = false;
    } else if (!currentValue.includes('.')) {
        currentValue += '.';
    }
    clearOperatorHighlight();
    updateDisplay();
}

function toggleSign() {
    currentValue = (parseFloat(currentValue) * -1).toString();
    updateDisplay();
}

function inputPercent() {
    currentValue = (parseFloat(currentValue) / 100).toString();
    updateDisplay();
}

function clearAll() {
    currentValue = '0';
    previousValue = null;
    operator = null;
    waitingForOperand = false;
    clearOperatorHighlight();
    updateDisplay();
}

function clearOperatorHighlight() {
    document.querySelectorAll('.op.active').forEach(b => b.classList.remove('active'));
}

async function setOperator(op, btn) {
    if (operator && !waitingForOperand) {
        await compute();
    }
    previousValue = currentValue;
    operator = op;
    waitingForOperand = true;
    clearOperatorHighlight();
    if (btn) btn.classList.add('active');
}

async function compute() {
    if (operator === null || previousValue === null) return;
    const a = parseFloat(previousValue);
    const b = parseFloat(currentValue);
    try {
        const res = await fetch(`/api/${operator}?a=${a}&b=${b}`);
        currentValue = res.ok ? await res.text() : 'Erreur';
    } catch (e) {
        currentValue = 'Erreur';
    }
    previousValue = null;
    operator = null;
    waitingForOperand = true;
    clearOperatorHighlight();
    updateDisplay();
}

document.addEventListener('keydown', (e) => {
    if (e.key >= '0' && e.key <= '9') inputDigit(e.key);
    else if (e.key === '.' || e.key === ',') inputDecimal();
    else if (e.key === '+') void setOperator('add', document.querySelector('[data-op="add"]'));
    else if (e.key === '-') void setOperator('subtract', document.querySelector('[data-op="subtract"]'));
    else if (e.key === '*') void setOperator('multiply', document.querySelector('[data-op="multiply"]'));
    else if (e.key === '/') {
        e.preventDefault();
        void setOperator('divide', document.querySelector('[data-op="divide"]'));
    } else if (e.key === 'Enter' || e.key === '=') void compute();
    else if (e.key === 'Escape' || e.key.toLowerCase() === 'c') clearAll();
});

updateDisplay();
