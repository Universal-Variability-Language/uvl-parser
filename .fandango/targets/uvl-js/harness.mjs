// Parse one file with the JavaScript parser of this checkout (`make js_parser` and `npm install` done):
// exit 0 if it parses.
import { FeatureModel } from "../../../js/index.js";

try {
  new FeatureModel(process.argv[2]);
} catch (error) {
  const message = String(error?.message ?? error).split(/\s+/).join(" ");
  console.error(`${error?.name ?? "Error"}: ${message}`);
  process.exit(1);
}
