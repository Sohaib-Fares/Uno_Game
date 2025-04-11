package AppTools.UI.Components;

import AppTools.CardModel.*;
import AppTools.UI.UnoTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class UnoCardComponent extends JPanel {
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 180;
    private static final int CORNER_RADIUS = 16;

    private AbstractCard card;
    private boolean faceDown = false;

    public UnoCardComponent(AbstractCard card) {
        this.card = card;
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setOpaque(false);
    }

    public UnoCardComponent() {
        this.card = null;
        this.faceDown = true;
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setOpaque(false);
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
        repaint();
    }

    public void setCard(AbstractCard card) {
        this.card = card;
        this.faceDown = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw card shadow
        drawCardShadow(g2);

        if (faceDown) {
            drawFaceDownCard(g2);
        } else if (card != null) {
            drawFaceUpCard(g2);
        }

        g2.dispose();
    }

    private void drawCardShadow(Graphics2D g2) {
        for (int i = 3; i > 0; i--) {
            float alpha = 0.3f * (float) i / 3;
            g2.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
            g2.fill(new RoundRectangle2D.Double(2 + i, 2 + i, CARD_WIDTH - 4, CARD_HEIGHT - 4, CORNER_RADIUS,
                    CORNER_RADIUS));
        }
    }

    private void drawFaceDownCard(Graphics2D g2) {
        // Draw card back
        g2.setColor(UnoTheme.UNO_RED);
        g2.fill(new RoundRectangle2D.Double(2, 2, CARD_WIDTH - 4, CARD_HEIGHT - 4, CORNER_RADIUS, CORNER_RADIUS));

        // Draw border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new RoundRectangle2D.Double(4, 4, CARD_WIDTH - 8, CARD_HEIGHT - 8, CORNER_RADIUS - 2,
                CORNER_RADIUS - 2));

        // Draw UNO logo
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString("UNO", (CARD_WIDTH - fm.stringWidth("UNO")) / 2, CARD_HEIGHT / 2 + fm.getAscent() / 2);
    }

    private void drawFaceUpCard(Graphics2D g2) {
        // Get card color
        Color cardColor;
        if (card instanceof WildCard && ((WildCard) card).getColor() == null) {
            cardColor = Color.BLACK;
        } else {
            cardColor = getColorForCard(card.getColor());
        }

        // Draw card background
        g2.setColor(cardColor);
        g2.fill(new RoundRectangle2D.Double(2, 2, CARD_WIDTH - 4, CARD_HEIGHT - 4, CORNER_RADIUS, CORNER_RADIUS));

        // Draw white oval
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(10, 40, CARD_WIDTH - 20, CARD_HEIGHT - 80));

        // Draw card content
        drawCardContent(g2, cardColor);
    }

    private void drawCardContent(Graphics2D g2, Color cardColor) {
        g2.setColor(cardColor);
        g2.setFont(new Font("Arial", Font.BOLD, 40));

        if (card instanceof NumberedCard) {
            drawNumberedCard(g2, (NumberedCard) card);
        } else if (card instanceof ActionCard) {
            drawActionCard(g2, (ActionCard) card);
        } else if (card instanceof WildCard) {
            drawWildCard(g2, (WildCard) card);
        }
    }

    private void drawNumberedCard(Graphics2D g2, NumberedCard card) {
        String number = String.valueOf(card.getValue());
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(number, (CARD_WIDTH - fm.stringWidth(number)) / 2, CARD_HEIGHT / 2 + fm.getAscent() / 2);

        // Draw small numbers in corners
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(number, 8, 24);
        g2.drawString(number, CARD_WIDTH - 22, CARD_HEIGHT - 8);
    }

    private void drawActionCard(Graphics2D g2, ActionCard card) {
        String symbol = "";
        switch (card.getType()) {
            case SKIP:
                symbol = "⊘";
                break;
            case REVERSE:
                symbol = "⇄";
                break;
            case DRAW_TWO:
                symbol = "+2";
                break;
            default:
                break;
        }

        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(symbol, (CARD_WIDTH - fm.stringWidth(symbol)) / 2, CARD_HEIGHT / 2 + fm.getAscent() / 2);

        // Draw small symbols in corners
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(symbol, 8, 24);
        g2.drawString(symbol, CARD_WIDTH - 22, CARD_HEIGHT - 8);
    }

    private void drawWildCard(Graphics2D g2, WildCard card) {
        String symbol = card.getType() == CardTypeEnum.WILD_COLOR ? "W" : "+4";

        // Draw colored quadrants if it's a wild card
        if (card.getColor() == null) {
            drawWildQuadrants(g2);
        }

        // Draw the symbol
        g2.setColor(card.getColor() == null ? Color.WHITE : Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(symbol, (CARD_WIDTH - fm.stringWidth(symbol)) / 2, CARD_HEIGHT / 2 + fm.getAscent() / 2);

        // Draw small symbols in corners
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(symbol, 8, 24);
        g2.drawString(symbol, CARD_WIDTH - 22, CARD_HEIGHT - 8);
    }

    private void drawWildQuadrants(Graphics2D g2) {
        int size = Math.min(CARD_WIDTH - 30, CARD_HEIGHT - 80);
        int x = (CARD_WIDTH - size) / 2;
        int y = (CARD_HEIGHT - size) / 2;

        g2.setColor(UnoTheme.UNO_RED);
        g2.fillArc(x, y, size, size, 0, 90);

        g2.setColor(UnoTheme.UNO_BLUE);
        g2.fillArc(x, y, size, size, 90, 90);

        g2.setColor(UnoTheme.UNO_YELLOW);
        g2.fillArc(x, y, size, size, 180, 90);

        g2.setColor(UnoTheme.UNO_GREEN);
        g2.fillArc(x, y, size, size, 270, 90);
    }

    private Color getColorForCard(CardColorEnum cardColor) {
        if (cardColor == null)
            return Color.BLACK;

        return switch (cardColor) {
            case RED -> UnoTheme.UNO_RED;
            case BLUE -> UnoTheme.UNO_BLUE;
            case GREEN -> UnoTheme.UNO_GREEN;
            case YELLOW -> UnoTheme.UNO_YELLOW;
            default -> Color.BLACK;
        };
    }
}