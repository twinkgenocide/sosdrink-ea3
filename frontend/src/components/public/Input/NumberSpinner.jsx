import { NeonButton } from "./Buttons";
import "./NumberSpinner.css";

export function NumberSpinner({ value, setValue, min = 1, max = 10, disabled = false }) {
    const increment = () => {
        if (disabled) return;
        setValue(Math.min(max, value + 1));
    }
    const decrement = () => {
        if (disabled) return;
        setValue(Math.max(min, value - 1));
    }

    return <div className={`num-spinner ${disabled ? 'disabled' : ''}`}>
        <NeonButton onClick={decrement}>-</NeonButton>
        <input
            type="number"
            value={value}
            min={min}
            max={max}
            disabled={true}
        />
        <NeonButton onClick={increment}>+</NeonButton>
    </div>
}
