import { CSSProperties } from "react";

function Label({
  text,
  style = {},
  renderHtml = false,
}: {
  text: string;
  style?: CSSProperties;
  renderHtml?: boolean;
}) {
  // The _intention_ is to have this component seamlessly transition between states, hence pulling it out into its own component
  if (renderHtml) {
    return (
      <span
        style={{ position: "relative", ...style }}
        dangerouslySetInnerHTML={{ __html: text }}
      />
    );
  }
  return <span style={{ position: "relative", ...style }}>{text}</span>;
}

export default Label;
