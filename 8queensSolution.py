import random
board = [[0] * 8 for _ in range(8)]
count = 1
"""
    for row in range(8):
        for col in range(8):
            board[row][col] = count
            count += 1
    show_board(board)
"""



#debug personal reference notes
def show_board(board): #used to show the current arrangment in board mainly for debug
    for row in board:
        for item in row:
            print(item, end=" ")
        print()
   # print(board)
    print("---------")

#https://www.programiz.com/python-programming/methods/built-in/sum
#https://stackoverflow.com/questions/16548668/iterating-over-a-2-dimensional-python-list
#https://www.geeksforgeeks.org/python-using-2d-arrays-lists-the-right-way/
#https://stackoverflow.com/questions/15100735/checking-diagonals-in-2d-list-python












def generate_initial_solution(board):    #creates the starting point board before any changes made

    # RANDOMLY PLACES QUEENS makes sure to only place one in each column
    for col in range(8):
        row = random.randint(0, 7)
        board[row][col] = 1




def heuristic_function(board):
    conflicts = 0

    for row in range(8):
        for col in range(8):
            if board[row][col] == 1:
                # Check horizontal conflicts
                for c in range(col + 1, 8):
                    if board[row][c] == 1:
                        conflicts += 1

                # Check diagonal conflicts (up-right)
                r = row - 1
                c = col + 1
                while r-1 >= 0 and c < 8:
                    if board[r][c] == 1:
                        conflicts += 1
                    r -= 1
                    c += 1

                # Check diagonal conflicts (down-right)
                r = row + 1
                c = col + 1
                while r < 8 and c < 8:
                    if board[r][c] == 1:
                        conflicts += 1
                    r += 1
                    c += 1

    #print("Number of conflicts:", conflicts)
   # show_board(board)
    return conflicts



def hill_climbing_with_random_restart(board):
    current_score = heuristic_function(board)
    restart_attempts = 0
    lower_score_boards = 0

    while current_score > 0 and restart_attempts <100:
        neighbors = generate_neighboring_configurations(board)

        best_neighbor = board  # Initialize the best neighbor as the current board
        best_score = current_score

        for itteration in neighbors:
            neighbor_score = heuristic_function(itteration)
            if neighbor_score < best_score:
                best_neighbor = itteration
                best_score = neighbor_score
                lower_score_boards += 1
                print("Current board state")
                show_board(itteration)
                print("Current number conflicts", best_score)
                print("current restar value", restart_attempts)
                print("setting new board state")

            #show_board(board)
        if best_score >= current_score:
            print("Best new board was worse then current board... restarting")
            # If the best neighbor does not have a better score than the current board, perform random restart
            generate_initial_solution(board)
            current_score = heuristic_function(board)
           # show_board(board)
            restart_attempts += 1
        else:
            board = best_neighbor
            current_score = best_score

    if current_score == 0:

        print("Solution found!")
        show_board(board)
    else:
        print("Reached maximum restart attempts without finding a solution.")

def generate_neighboring_configurations(board):
    neighbors = []
    #show_board(board)
    for col in range(8):
        for row in range(8):
            if board[row][col] == 1:
                # Remove the queen from the current position
                board[row][col] = 0

                # Move the queen to each row in the column and add the new configuration to neighbors
                for new_row in range(8):
                    if new_row != row:
                        new_board = [row[:] for row in board]  # Create a copy of the board
                        new_board[new_row][col] = 1
                        neighbors.append(new_board)

                # Restore the queen to the current position for the next iteration
                board[row][col] = 1

    return neighbors


#show_board(board)
generate_initial_solution(board)

initial_score = heuristic_function(board)
hill_climbing_with_random_restart(board)