export default class SyntaxGenericError extends Error {
  constructor(payload) {
    super(payload);

    this.code = "E_SYNTAX_GENERIC";
    this.message = "Something went wrong";

    if (typeof payload !== "undefined") {
      this.message = payload.message || this.message;
      this.payload = payload;
    }

    console.error(
      `line ${this.payload.line}, col ${this.payload.column}: ${this.payload.message}`,
    );
    Error.captureStackTrace(this, SyntaxGenericError);
  }
}
