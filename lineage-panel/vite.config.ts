import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    // to build the React app into the resources folder of the IntelliJ plugin //
    outDir: process.env.VITE_OUTPUT_DIR || '../src/main/resources/lineage-panel-dist',
    emptyOutDir: true,
    rollupOptions: {
      output: {
        // fixed names without hashes to easily add request handlers in Cef //
        entryFileNames: '[name].js',
        chunkFileNames: '[name].js',
        assetFileNames: '[name].[ext]'
      }
    }
  }
});
