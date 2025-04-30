package UI;

import UI.Constatnts.MuColors;
import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Cards.MuNumberedCard;
import UI.Components.Cards.MuWildCard;
import UI.Components.Containers.MuScrollPane;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuBox;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Misc.MuSeparator;
import UI.Components.Panels.MuPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MuHowToPlayPanel extends JPanel {

        private static final Font FONT_H2P_TITLE = new Font("Lato", Font.BOLD, 25);

        // modern Scroll pane
        private MuScrollPane createModernScrollPane(Component view) {
                MuScrollPane scrollPane = new MuScrollPane(view);
                scrollPane.setVerticalScrollBarPolicy(MuScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setHorizontalScrollBarPolicy(MuScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
                scrollPane.getViewport().setOpaque(true);
                scrollPane.setOpaque(false);
                BasicScrollBarUI scrollbar1 = new BasicScrollBarUI() {
                        private final Dimension zeroDim = new Dimension(0, 0);

                        @Override
                        protected void configureScrollBarColors() {
                                this.thumbColor = new Color(100, 100, 100, 180);
                                this.trackColor = new Color(230, 230, 230, 50);
                        }

                        @Override
                        protected JButton createDecreaseButton(int orientation) {
                                return createZeroButton();
                        }

                        @Override
                        protected JButton createIncreaseButton(int orientation) {
                                return createZeroButton();
                        }

                        private JButton createZeroButton() {
                                JButton button = new JButton();
                                button.setPreferredSize(zeroDim);
                                button.setMinimumSize(zeroDim);
                                button.setMaximumSize(zeroDim);
                                return button;
                        }

                        @Override
                        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                                        return;
                                }
                                Graphics2D g2 = (Graphics2D) g.create();
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g2.setColor(thumbColor);
                                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4,
                                                thumbBounds.height - 4,
                                                10, 10);
                                g2.dispose();
                        }

                        @Override
                        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                                Graphics2D g2 = (Graphics2D) g.create();
                                g2.setColor(trackColor);
                                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                                g2.dispose();
                        }
                };
                scrollPane.getVerticalScrollBar().setUI(scrollbar1);

                scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
                scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                return scrollPane;
        }

        public MuHowToPlayPanel(MuMainFrame mainFrame) {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                setOpaque(false);

                // Set preferred and maximum size
                setPreferredSize(new Dimension(800, 600));
                setMinimumSize(new Dimension(600, 400));
                // Add this line to set maximum size
                setMaximumSize(new Dimension(900, 700));

                // Center the panel in its container
                JPanel centeringPanel = new JPanel(new GridBagLayout());
                centeringPanel.setOpaque(false);

                // Make the panel use all available space
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                // borders for the sub panels
                Border softBorder = BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(236, 237, 236), 2),
                                BorderFactory.createEmptyBorder(0, 0, -8, 0));

                // head panel conatins the photo and back button
                JPanel headPanel = new JPanel(new GridBagLayout());
                GridBagConstraints headGbc = new GridBagConstraints(); // Use a separate GBC for headPanel

                headPanel.setBackground(new Color(185, 64, 14));
                // headPanel.setBorder(new EmptyBorder(10, 1, 10, 10));

                MuOutlinedButton backButton = new MuOutlinedButton("Back", MuColors.MuYellow, Color.BLACK, 20, 80, 80,
                                3, MuColors.black);
                backButton.addActionListener(e -> mainFrame.switchToPanel(MuMainFrame.MENU_PANEL));
                backButton.setPreferredSize(new Dimension(100, 70));

                headGbc.gridx = 0;
                headGbc.gridy = 0;
                headGbc.weightx = 0; // Don't let the button stretch horizontally
                headGbc.weighty = 0;
                headGbc.anchor = GridBagConstraints.WEST; // Align button to the left
                headGbc.insets = new Insets(0, 0, 0, 10); // Add some padding to the right

                headPanel.add(backButton, headGbc);

                MuLabel JUNO;
                MuImageIcon originalIcon = new MuImageIcon("src/main/java/UI/Assets/JUNO.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JUNO = new MuLabel(new MuImageIcon(scaledImage));

                headGbc.gridx = 1;
                headGbc.gridy = 0;
                headGbc.weightx = 1.0; // Let the logo container take up the remaining space
                headGbc.anchor = GridBagConstraints.CENTER; // Center the logo within its cell

                headPanel.add(JUNO, headGbc);

                // Add a dummy component to balance the layout and push the logo to the center
                // It should ideally take up the same horizontal space as the button
                headGbc.gridx = 2;
                headGbc.gridy = 0;
                headGbc.weightx = 0; // Don't let the spacer stretch
                headGbc.anchor = GridBagConstraints.WEST;
                headPanel.add(MuBox.createHorizontalStrut(backButton.getPreferredSize().width), headGbc);

                // panel contains instructions
                JPanel centerContentWrapper = new JPanel();
                centerContentWrapper.setLayout(new BoxLayout(centerContentWrapper, BoxLayout.Y_AXIS));
                centerContentWrapper.setOpaque(true);
                centerContentWrapper.setBackground(Color.WHITE); // Set background for the main content wrapper
                centerContentWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

                // Create a new panel JUST for the How To Play label (with FlowLayout for
                // centering)
                JPanel labelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
                labelContainer.setBackground(Color.WHITE); // Match background
                labelContainer.setMaximumSize(
                                new Dimension(Integer.MAX_VALUE, labelContainer.getPreferredSize().height)); // Prevent
                                                                                                             // stretching

                MuLabel titleLabel = new MuLabel("How to Play");
                titleLabel.setFont(FONT_H2P_TITLE);
                titleLabel.setForeground(Color.RED);
                titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
                labelContainer.add(titleLabel);

                // create objective panel
                JPanel objectivePanel = MuPanel.createInformationPanel("Objective",
                                "Be the first player to get rid of all your cards! When you have only one card left, don't forget to shout \"UNO!\"");
                objectivePanel.add(MuBox.createRigidArea(new Dimension(0, 8)));

                // create setup panel
                JPanel setupPanel = MuPanel.createInformationPanel("Setup",
                                "Each player is start with 7 cards. The remaining cards from the draw pile. The top card is turned over to start the discard pile");
                setupPanel.add(MuBox.createRigidArea(new Dimension(0, 8)));

                // create gameplay panel
                JPanel gameplayPanel = MuPanel.createInformationPanel("Gameplay",
                                "<div style='width: 350px; text-align: left;'>" +
                                                "<b>On your turn</b>, you must match the top card by either:<br><br>" +
                                                "&bull; Playing a card of the <b>same color</b><br>" +
                                                "&bull; Playing a card with the <b>same number/symbol</b><br>" +
                                                "&bull; Playing a <b>Wild</b> or <b>Wild Draw Four</b><br><br>" +
                                                "If you can't play, draw from the pile. If playable, you may use it immediately."
                                                +
                                                "</div>");
                gameplayPanel.add(MuBox.createRigidArea(new Dimension(0, 8)));

                // create winning panel
                JPanel winningPanel = MuPanel.createInformationPanel("Winning",
                                "The first player to get rid of all their cards wins the round! If you forget to say \"UNO\" when you have one card left to play and another player ctches you, you must draw 2 cards as a penalty.");
                winningPanel.add(MuBox.createRigidArea(new Dimension(0, 8)));

                objectivePanel.setBorder(softBorder);
                setupPanel.setBorder(softBorder);
                gameplayPanel.setBorder(softBorder);
                winningPanel.setBorder(softBorder);

                // create card types panel using the function
                JPanel cardTypesPanel = addCardTypes();
                cardTypesPanel.setOpaque(false);

                // Set background color for the card types panel
                cardTypesPanel.setBackground(Color.WHITE); // Set background to white

                cardTypesPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
                cardTypesPanel.setOpaque(true); // Make it opaque to show background
                cardTypesPanel.setBorder(softBorder); // Apply border here

                // Add components in order with Box layout
                centerContentWrapper.add(headPanel);
                centerContentWrapper.add(labelContainer);
                centerContentWrapper.add(objectivePanel);
                centerContentWrapper.add(MuBox.createRigidArea(new Dimension(0, 15)));
                centerContentWrapper.add(setupPanel);
                centerContentWrapper.add(MuBox.createRigidArea(new Dimension(0, 15)));
                centerContentWrapper.add(gameplayPanel);
                centerContentWrapper.add(MuBox.createRigidArea(new Dimension(0, 15)));
                centerContentWrapper.add(cardTypesPanel);
                centerContentWrapper.add(MuBox.createRigidArea(new Dimension(0, 15)));
                centerContentWrapper.add(winningPanel);

                // Set alignmentX for all components to ensure proper alignment in BoxLayout
                objectivePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                setupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                gameplayPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                winningPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                labelContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
                headPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                headPanel.setBorder(BorderFactory.createCompoundBorder(
                                new MatteBorder(2, 2, 0, 2, new Color(238, 239, 239)),
                                new EmptyBorder(10, 10, 10, 10)));
                centerContentWrapper.setBorder(BorderFactory.createCompoundBorder(
                                new MatteBorder(0, 2, 2, 2, new Color(238, 239, 239)),
                                new EmptyBorder(10, 10, 10, 10)));

                // the panel that is bordered by grey border
                JPanel borderedContent = new JPanel();
                borderedContent.setLayout(new BoxLayout(borderedContent, BoxLayout.Y_AXIS)); // FOR ALL PANELs
                borderedContent.setOpaque(false);
                borderedContent.add(headPanel);
                borderedContent.add(centerContentWrapper);

                // Modify scrollPane settings to properly handle resizing
                MuScrollPane scrollPane = createModernScrollPane(borderedContent);
                scrollPane.setPreferredSize(new Dimension(800, 600)); // Smaller preferred size
                scrollPane.setMinimumSize(new Dimension(600, 400)); // Reasonable minimum size

                // Add viewport resize listener to handle content resizing
                scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                                // Recalculate preferred size of content based on viewport width
                                int viewportWidth = scrollPane.getViewport().getWidth();
                                if (viewportWidth > 0) {
                                        borderedContent.setPreferredSize(new Dimension(
                                                        viewportWidth - 20, // Account for scrollbar width
                                                        borderedContent.getPreferredSize().height));
                                        borderedContent.revalidate();
                                }
                        }
                });

                // Update the GridBagConstraints for the scrollPane
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                add(scrollPane, gbc);

        }

        // function to creat cardTypesPanel
        private JPanel addCardTypes() {

                JPanel warper = new JPanel(new GridBagLayout());
                GridBagConstraints warperGbc = new GridBagConstraints();
                warper.setOpaque(false);

                JPanel titleArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
                titleArea.setOpaque(true); // Keep this transparent so parent background shows
                titleArea.setBackground(new Color(254, 250, 234));

                MuLabel cardTypesTitleLabel = new MuLabel("Card Types");
                cardTypesTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                cardTypesTitleLabel.setForeground(new Color(145, 63, 12)); // light text
                cardTypesTitleLabel.setOpaque(false);
                cardTypesTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adjusted padding

                titleArea.add(cardTypesTitleLabel);

                // Separator below title
                MuSeparator separator = new MuSeparator();
                separator.setForeground(new Color(200, 200, 200));
                // manager handle size

                JPanel contentArea = new JPanel(new GridBagLayout());
                GridBagConstraints contentGbc = new GridBagConstraints();
                contentArea.setOpaque(false); // Keep this transparent so parent background shows
                contentArea.setBorder(new EmptyBorder(10, 10, 10, 10));
                MuNumberedCard redzero = new MuNumberedCard(MuColors.MuRed, "0");
                MuNumberedCard yellowskipCard = new MuNumberedCard(MuColors.MuYellow, "⊘");
                MuNumberedCard reversegreen = new MuNumberedCard(MuColors.MuGreen, "⟲");
                MuNumberedCard blueplus2 = new MuNumberedCard(MuColors.MuBlue, "+2");
                MuWildCard wild = new MuWildCard("W");
                MuWildCard wilddrawfour = new MuWildCard("+4");

                contentArea.setBackground(Color.WHITE); // Keep content area background white
                // --- 3x2 Grid Layout ---
                contentGbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
                contentGbc.anchor = GridBagConstraints.NORTHWEST; // Anchor top-left
                contentGbc.insets = new Insets(5, 5, 15, 5); // Add padding between cells (top, left, bottom, right)
                contentGbc.weightx = 0.5; // Distribute horizontal space equally between 2 columns
                contentGbc.weighty = 0; // Don't stretch vertically

                // Row 0
                contentGbc.gridx = 0;
                contentGbc.gridy = 0;
                contentArea.add(createCardWithDescription(redzero,
                                "Number cards - Play a card that matches the color or number of the discard pile."),
                                contentGbc);

                contentGbc.gridx = 1;
                contentGbc.gridy = 0;
                contentArea.add(createCardWithDescription(yellowskipCard, "Skip - The next player loses their turn."),
                                contentGbc);

                // Row 1
                contentGbc.gridx = 0;
                contentGbc.gridy = 1;
                contentArea.add(createCardWithDescription(reversegreen, "Reverse - Reverses the direction of play."),
                                contentGbc);

                contentGbc.gridx = 1;
                contentGbc.gridy = 1;
                contentArea.add(createCardWithDescription(blueplus2,
                                "Draw Two - The next player must draw 2 cards and lose their turn."), contentGbc);

                // Row 2
                contentGbc.gridx = 0;
                contentGbc.gridy = 2;
                contentArea.add(createCardWithDescription(wild,
                                "Wild - Change the current color to any color of your choice."), contentGbc);

                contentGbc.gridx = 1;
                contentGbc.gridy = 2;
                contentArea.add(createCardWithDescription(wilddrawfour,
                                "Wild Draw Four - Change the color and make the next player draw 4 cards."),
                                contentGbc);

                // --- Add components to warper panel ---
                warperGbc.gridx = 0; // Title spans the whole width
                warperGbc.gridy = 0;
                warperGbc.gridwidth = 0; // Changed from 1
                warperGbc.fill = GridBagConstraints.HORIZONTAL;
                warper.add(titleArea, warperGbc);

                warperGbc.gridx = 0; // Separator below title
                warperGbc.gridy = 1;
                warperGbc.gridwidth = 1; // Span width
                warperGbc.fill = GridBagConstraints.HORIZONTAL;
                warper.add(separator, warperGbc);

                warperGbc.gridx = 0; // Content area below separator
                warperGbc.gridy = 2; // Changed from 1
                warperGbc.gridwidth = 1; // Span width
                warperGbc.fill = GridBagConstraints.BOTH; // Fill available space
                warperGbc.weightx = 1.0;
                warperGbc.weighty = 1.0; // Allow vertical expansion if needed
                warper.add(contentArea, warperGbc);

                return warper;
        }

        private JPanel createCardWithDescription(JComponent card, String description) {
                JPanel rowPanel = new JPanel();
                // Use GridBagLayout for better control within the cell
                rowPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbcRow = new GridBagConstraints();
                rowPanel.setOpaque(false); // Keep row panels transparent

                // Fix card size and prevent resizing
                card.setPreferredSize(new Dimension(45, 70));
                card.setMinimumSize(new Dimension(45, 70));
                card.setMaximumSize(new Dimension(45, 70)); // Changed from setMinimumSize to setMaximumSize

                // GridBagLayout

                // Wrap card in a fixed-size panel to maintain its dimensions
                JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                cardContainer.setOpaque(false);
                cardContainer.add(card);
                cardContainer.setPreferredSize(new Dimension(100, 150)); // Slightly larger than card to allow for
                                                                         // margins
                cardContainer.setMinimumSize(new Dimension(100, 150));
                cardContainer.setMaximumSize(new Dimension(100, 150));

                MuLabel descLabel = new MuLabel(
                                "<html><body style='width: 180px; text-align: left;'>" + description
                                                + "</body></html>");
                descLabel.setFont(new Font("Lato", Font.BOLD, 12));
                descLabel.setForeground(Color.BLACK);
                descLabel.setVerticalAlignment(MuLabel.TOP); // Align text to the top

                // Add card container instead of card directly
                gbcRow.gridx = 0;
                gbcRow.gridy = 0;
                gbcRow.anchor = GridBagConstraints.NORTHWEST; // Anchor card top-left
                gbcRow.insets = new Insets(0, 0, 0, 10); // Padding between card and text
                gbcRow.weightx = 0; // Card takes fixed width
                gbcRow.fill = GridBagConstraints.NONE;
                rowPanel.add(cardContainer, gbcRow);

                // Add description
                gbcRow.gridx = 1;
                gbcRow.gridy = 0;
                gbcRow.anchor = GridBagConstraints.NORTHWEST; // Anchor text top-left
                gbcRow.insets = new Insets(0, 0, 0, 0); // Reset insets
                gbcRow.weightx = 1.0; // Text takes remaining width
                gbcRow.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
                rowPanel.add(descLabel, gbcRow);

                return rowPanel;

        }

}
