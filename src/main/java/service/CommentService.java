    package service;

    import model.Comment;
    import utils.MyDataBase;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class CommentService implements IService<Comment> {
        Connection connection;
        public CommentService() {
            connection = MyDataBase.getInstance().getConnection();
        }

        public int add(Comment comment) throws SQLException {
            String sql = "INSERT INTO comment (email, name, text, rating,activity_id,created_at) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, comment.getEmail());
            preparedStatement.setString(2, comment.getName());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setInt(4, comment.getRating());
            preparedStatement.setInt(5, comment.getActivity());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            return 0;
        }

        @Override
        public void create(Comment comment) throws SQLException {

        }

        @Override
        public Comment authenticate(String email, String password) throws SQLException {
            return null;
        }

        @Override
        public void update(Comment comment) throws SQLException {
            String sql = "UPDATE comment SET email=?, name=?, text=?, rating=?, activity_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, comment.getEmail());
            preparedStatement.setString(2, comment.getName());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setInt(4, comment.getRating());
            preparedStatement.setInt(5, comment.getId());
            preparedStatement.setInt(6, comment.getActivity());
            preparedStatement.executeUpdate();
        }

        @Override
        public void delete(int id) throws SQLException {
            String sql = "DELETE FROM comment WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

        @Override
        public List<Comment> read() throws SQLException {
            return null;
        }

        public List<Comment> select() throws SQLException {
            List<Comment> comments = new ArrayList<>();
            String sql = "SELECT * FROM comment";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setEmail(resultSet.getString("email"));
                comment.setName(resultSet.getString("name"));
                comment.setText(resultSet.getString("text"));
                comment.setRating(resultSet.getInt("rating"));
                comment.setCreatedAt(resultSet.getTimestamp("created_at"));
                comment.setActivity(resultSet.getInt("activity_Id"));
                comments.add(comment);
            }
            return comments;
        }

        public Comment selectById(int id) throws SQLException {
            List<Comment> comments = new ArrayList<>();
            String sql = "SELECT * FROM comment where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setEmail(resultSet.getString("email"));
                comment.setName(resultSet.getString("name"));
                comment.setText(resultSet.getString("text"));
                comment.setRating(resultSet.getInt("rating"));
                comment.setCreatedAt(resultSet.getTimestamp("created_at"));
                comment.setActivity(resultSet.getInt("activity_Id"));
                return comment;
            }
            return null;
        }

        public List<Comment> selectByActivityId(int activityId) throws SQLException {
            List<Comment> comments = new ArrayList<>();
            String sql = "SELECT * FROM comment where activity_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setEmail(resultSet.getString("email"));
                comment.setName(resultSet.getString("name"));
                comment.setText(resultSet.getString("text"));
                comment.setRating(resultSet.getInt("rating"));
                comment.setCreatedAt(resultSet.getTimestamp("created_at"));
                comment.setActivity(resultSet.getInt("activity_Id"));
                comments.add(comment);
            }
            return comments;
        }

    }
