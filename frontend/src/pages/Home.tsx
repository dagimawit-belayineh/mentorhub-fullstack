import { Box, Button, Typography, AppBar, Toolbar, Container, Stack } from "@mui/material";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        background: "linear-gradient(135deg, #eef2f3 0%, #ffffff 100%)",
        color: "#1a202c",
        fontFamily: "'Poppins', sans-serif",
      }}
    >
      {/* Navbar */}
      <AppBar
        position="static"
        elevation={0}
        sx={{
          background: "transparent",
          color: "#1a202c",
          borderBottom: "1px solid #e0e0e0",
        }}
      >
        <Toolbar>
          <Typography
            variant="h5"
            sx={{ flexGrow: 1, fontWeight: 700, color: "#1976d2" }}
          >
            MentorHub
          </Typography>
          <Stack direction="row" spacing={2}>
            <Button
              component={Link}
              to="/login"
              sx={{
                textTransform: "none",
                color: "#1a202c",
                fontWeight: 500,
              }}
            >
              Login
            </Button>
            <Button
              variant="contained"
              component={Link}
              to="/register"
              sx={{
                textTransform: "none",
                backgroundColor: "#1976d2",
                borderRadius: "8px",
                fontWeight: 600,
                px: 3,
                "&:hover": { backgroundColor: "#125ea5" },
              }}
            >
              Get Started
            </Button>
          </Stack>
        </Toolbar>
      </AppBar>

      {/* Hero Section */}
      <Container
        maxWidth="md"
        sx={{
          textAlign: "center",
          mt: 10,
          px: 3,
        }}
      >
        <Typography
          variant="h2"
          sx={{
            fontWeight: 700,
            color: "#1a202c",
            mb: 2,
            fontSize: { xs: "2rem", md: "3rem" },
          }}
        >
          Grow Faster with the Right Mentor
        </Typography>

        <Typography
          variant="h6"
          sx={{
            color: "#4a5568",
            mb: 4,
            maxWidth: 600,
            mx: "auto",
            lineHeight: 1.6,
          }}
        >
          Connect with experienced professionals to get personalized mentorship in tech, design, and business.
        </Typography>

        <Stack
          direction={{ xs: "column", sm: "row" }}
          spacing={2}
          justifyContent="center"
        >
          <Button
            variant="contained"
            component={Link}
            to="/register"
            sx={{
              backgroundColor: "#1976d2",
              borderRadius: "8px",
              fontWeight: 600,
              px: 4,
              py: 1.5,
              textTransform: "none",
              "&:hover": { backgroundColor: "#125ea5" },
            }}
          >
            Find a Mentor
          </Button>
          <Button
            variant="outlined"
            component={Link}
            to="/login"
            sx={{
              borderColor: "#1976d2",
              color: "#1976d2",
              borderRadius: "8px",
              fontWeight: 600,
              px: 4,
              py: 1.5,
              textTransform: "none",
              "&:hover": {
                borderColor: "#125ea5",
                color: "#125ea5",
              },
            }}
          >
            Become a Mentor
          </Button>
        </Stack>
      </Container>

      {/* Footer */}
      <Box sx={{ textAlign: "center", mt: 10, py: 3, color: "#718096" }}>
        <Typography variant="body2">
          © {new Date().getFullYear()} MentorHub. Built with ❤️ using React & Spring Boot.
        </Typography>
      </Box>
    </Box>
  );
}
