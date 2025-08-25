
import { defineConfig } from 'vitest/config';

export default defineConfig({
    test:{
        include:['src/tests/*.test.{ts,tsx}'],
        environment: 'happy-dom',
    }
});