import react from '@vitejs/plugin-react';
import { defineConfig } from 'vite';

// https://vitejs.dev/config/
export default defineConfig(() => {
  let outDir = process.env.VITE_OUTPUT_DIR;
  if (!outDir) {
    outDir = '../src/main/resources/lineage-panel-dist';
    console.info('VITE_OUTPUT_DIR not set via environment variables, using default value');
  }

  console.info(`VITE_OUTPUT_DIR: ${outDir}`);

  return {
    plugins: [react()],
    build: {
      outDir,
      emptyOutDir: true,
      rollupOptions: {
        output: {
          entryFileNames: '[name].js',
          chunkFileNames: '[name].js',
          assetFileNames: '[name].[ext]'
        }
      }
    }
  };
});
