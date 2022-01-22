module.exports = {
  outputDir: "../main/resources/static/spa",
  publicPath: process.env.NODE_ENV === "production" ? "/spa/" : "/",
  pages: {
    index: {
      entry: 'src/main.js',
      title: 'Online-Booking | Hotel Schwarz',
    },
  }
};
