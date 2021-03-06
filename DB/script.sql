USE [master]
GO
/****** Object:  Database [Sem2Project]    Script Date: 5/7/2017 11:41:13 AM ******/
CREATE DATABASE [Sem2Project]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Sem2Project', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL13.SQLEXPRESS\MSSQL\DATA\Sem2Project.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Sem2Project_log', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL13.SQLEXPRESS\MSSQL\DATA\Sem2Project_log.ldf' , SIZE = 4672KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Sem2Project] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Sem2Project].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Sem2Project] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Sem2Project] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Sem2Project] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Sem2Project] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Sem2Project] SET ARITHABORT OFF 
GO
ALTER DATABASE [Sem2Project] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Sem2Project] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Sem2Project] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Sem2Project] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Sem2Project] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Sem2Project] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Sem2Project] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Sem2Project] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Sem2Project] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Sem2Project] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Sem2Project] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Sem2Project] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Sem2Project] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Sem2Project] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Sem2Project] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Sem2Project] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Sem2Project] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Sem2Project] SET RECOVERY FULL 
GO
ALTER DATABASE [Sem2Project] SET  MULTI_USER 
GO
ALTER DATABASE [Sem2Project] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Sem2Project] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Sem2Project] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Sem2Project] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [Sem2Project] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Sem2Project] SET QUERY_STORE = OFF
GO
USE [Sem2Project]
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [Sem2Project]
GO
/****** Object:  UserDefinedFunction [dbo].[newCatID]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[newCatID]
(
	
)
RETURNS CHAR(3)
AS
BEGIN
	DECLARE @CURNUM INT;
	DECLARE @NEWNO CHAR(3)
		SELECT @CURNUM = COUNT (*) FROM Categories
		SET @NEWNO =FORMAT(@CURNUM,'000')
	RETURN @NEWNO;
END



GO
/****** Object:  UserDefinedFunction [dbo].[returnNextMemNo]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date, ,>
-- Description:	<Description, ,>
-- =============================================
CREATE FUNCTION [dbo].[returnNextMemNo]
(
	
)
RETURNS CHAR(8)
AS
BEGIN
	DECLARE @MAXINT INT;
	DECLARE @NEWNO CHAR(8)
	SELECT @MAXINT = (CONVERT(INT,RIGHT(M.Mem_No,7))+1) FROM Members m;
	IF(ISNULL(@MAXINT,0) =0)
		SET @NEWNO = 'M' + '0000001' ;
	ELSE
		SET @NEWNO = 'M' + RIGHT('0000000' + CONVERT(VARCHAR(7),@MAXINT),7) ;
	RETURN @NEWNO;
END



GO
/****** Object:  UserDefinedFunction [dbo].[Split]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	return table
-- =============================================
CREATE FUNCTION [dbo].[Split]
(	
	@String varchar(500) 
	, @Delimeter char(1)

)
RETURNS @tempTable TABLE (items VARCHAR(500)) 
AS
BEGIN
DECLARE @index int
DECLARE @slice varchar(500)

SELECT @index = 1
		IF LEN(@String) < 1 OR @String IS NULL RETURN
WHILE @index != 0
BEGIN
	SET @index = CHARINDEX(@Delimeter,@String)
	IF @index != 0
		SET @slice = LEFT(@String , @index - 1)
	ELSE
		SET @slice = @String
	IF(LEN(@slice) > 0)
		INSERT INTO @tempTable(ITEMS) VALUES (@slice)

	SET @String = RIGHT(@String,LEN(@String) - @index)
	IF LEN(@String) = 0 BREAK
END
RETURN 
END





GO
/****** Object:  Table [dbo].[Copies]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Copies](
	[Cop_Id] [char](36) NOT NULL,
	[Cop_No] [char](15) NOT NULL,
	[Book_ISBN] [varchar](8) NULL,
	[Cop_Status] [bit] NOT NULL,
	[Cop_isDeleted] [bit] NOT NULL,
 CONSTRAINT [PK__Copies__D0CC12DCB0E87CAD] PRIMARY KEY CLUSTERED 
(
	[Cop_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Categories]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[Cat_Id] [char](3) NOT NULL,
	[Cat_Name] [nvarchar](30) NOT NULL,
	[Cat_isDelete] [bit] NOT NULL,
	[Cat_Description] [nvarchar](100) NULL,
 CONSTRAINT [PK__Categori__26E35140860699DA] PRIMARY KEY CLUSTERED 
(
	[Cat_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Books]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Books](
	[Book_ISBN] [varchar](8) NOT NULL,
	[Book_Title] [nvarchar](100) NOT NULL,
	[Book_Publisher] [nvarchar](50) NULL,
	[Book_Author] [nvarchar](30) NULL,
	[Book_Price] [money] NOT NULL,
	[Book_Content] [nvarchar](1000) NOT NULL,
	[Cat_Id] [char](3) NULL,
	[Book_Language] [varchar](20) NOT NULL,
	[Book_ImageFile] [varchar](255) NOT NULL,
	[Book_CreateDate] [datetime] NOT NULL,
	[Book_isDeleted] [bit] NOT NULL,
 CONSTRAINT [PK__Books__74A104C9059C403F] PRIMARY KEY CLUSTERED 
(
	[Book_ISBN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[view_Book_Cate_Copy]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_Book_Cate_Copy]
AS
SELECT        B.Book_ISBN, B.Book_Title, B.Book_Publisher, B.Book_Author, B.Book_Price, B.Book_Content, C.Cat_Name, B.Book_Language, B.Book_ImageFile, B.Book_CreateDate, COP.Cop_No, COP.Cop_Status, 
                         COP.Cop_Id, COP.Cop_isDeleted, B.Book_isDeleted, B.Cat_Id
FROM            dbo.Books AS B LEFT OUTER JOIN
                         dbo.Categories AS C ON C.Cat_Id = B.Cat_Id LEFT OUTER JOIN
                         dbo.Copies AS COP ON COP.Book_ISBN = B.Book_ISBN




GO
/****** Object:  Table [dbo].[Members]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Members](
	[Mem_Id] [char](36) NOT NULL,
	[Mem_FirstName] [nvarchar](30) NOT NULL,
	[Mem_LastName] [nvarchar](50) NOT NULL,
	[Mem_No] [char](8) NOT NULL,
	[Mem_Phone] [varchar](20) NULL,
	[Mem_Address] [nvarchar](200) NULL,
	[Mem_Dep] [nvarchar](50) NOT NULL,
	[Mem_Status] [bit] NOT NULL,
	[Mem_CreateDate] [datetime] NOT NULL,
	[Mem_isDelete] [bit] NOT NULL,
	[Mem_ImageFile] [varchar](255) NOT NULL,
 CONSTRAINT [PK__Members__816CC11751B4F75D] PRIMARY KEY CLUSTERED 
(
	[Mem_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[IRBooks]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IRBooks](
	[IRBook_Id] [char](36) NOT NULL,
	[Mem_Id] [char](36) NULL,
	[IRBook_CreateDate] [datetime] NOT NULL,
	[IRBook_DueDate] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IRBook_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[IRBookDetails]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IRBookDetails](
	[IRBookDetail_Id] [char](36) NOT NULL,
	[IRBook_Id] [char](36) NULL,
	[Cop_Id] [char](36) NULL,
	[IRBookDetail_Status] [bit] NOT NULL,
	[IRBookDetail_ReturnDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[IRBookDetail_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[view_IR_IRDetail_Member]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_IR_IRDetail_Member]
AS
SELECT        IR.IRBook_Id, IR.Mem_Id, M.Mem_FirstName, M.Mem_LastName, M.Mem_No, M.Mem_Phone, M.Mem_Address, M.Mem_Dep, M.Mem_Status, M.Mem_CreateDate, M.Mem_isDelete, M.Mem_ImageFile, 
                         IR.IRBook_CreateDate, IR.IRBook_DueDate, IRD.IRBookDetail_Status, IRD.IRBookDetail_ReturnDate, IRD.Cop_Id, IRD.IRBookDetail_Id
FROM            dbo.IRBooks AS IR LEFT OUTER JOIN
                         dbo.Members AS M ON M.Mem_Id = IR.Mem_Id LEFT OUTER JOIN
                         dbo.IRBookDetails AS IRD ON IRD.IRBook_Id = IR.IRBook_Id



GO
/****** Object:  Table [dbo].[Fines]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Fines](
	[Fine_Id] [char](36) NOT NULL,
	[IRBookDetail_Id] [char](36) NOT NULL,
	[Fine_Amount] [money] NOT NULL,
	[Fine_PaidDate] [datetime] NULL,
	[Fine_Status] [bit] NOT NULL,
 CONSTRAINT [PK__Fines__B1609C336F42C9A3] PRIMARY KEY CLUSTERED 
(
	[Fine_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
INSERT [dbo].[Books] ([Book_ISBN], [Book_Title], [Book_Publisher], [Book_Author], [Book_Price], [Book_Content], [Cat_Id], [Book_Language], [Book_ImageFile], [Book_CreateDate], [Book_isDeleted]) VALUES (N'000-0001', N'weg', N'weg', N'weg', 100.0000, N'wegwe', N'000', N'Vietnamese', N'imgBook/20170501145735_DH.jpg', CAST(N'2017-05-01T14:58:29.750' AS DateTime), 0)
INSERT [dbo].[Books] ([Book_ISBN], [Book_Title], [Book_Publisher], [Book_Author], [Book_Price], [Book_Content], [Cat_Id], [Book_Language], [Book_ImageFile], [Book_CreateDate], [Book_isDeleted]) VALUES (N'000-0002', N'wegwe', N'wegw', N'weg', 322.0000, N'gregre', N'000', N'Vietnamese', N'imgBook/Nocover.jpg', CAST(N'2017-05-01T15:39:42.083' AS DateTime), 0)
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'000', N'English', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'001', N'Comic', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'002', N'Novel', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'003', N'Travel', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'004', N'Math', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'005', N'Dictionary', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'006', N'IT', 0, N'IT')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'007', N'History', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'008', N'Health', 0, N'')
INSERT [dbo].[Categories] ([Cat_Id], [Cat_Name], [Cat_isDelete], [Cat_Description]) VALUES (N'009', N'Finance', 0, N'')
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'01FC04AB-A543-4191-B626-2B1568A1813C', N'170501194622001', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'04883B66-01B3-42EC-A8BE-935F0F9DBD71', N'170501152120002', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'15494F12-3082-4D3F-8064-164BD1DE09E0', N'170501152120005', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'16E9157C-0386-43E3-9491-DC7B1CC95739', N'170501194623013', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'21A86391-E2B5-4F0E-925B-91685BCCB289', N'170501194622003', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'2997E7CE-B680-4DB1-9A0C-E4CCB8BC764D', N'170501194623007', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'2BA0BE76-5478-42D2-ABFF-5C81DF83CC70', N'170501194623014', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'338DE930-2522-45F3-B111-97344916401E', N'170501152120004', N'000-0001', 0, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'36BD2423-AE68-4447-AAD9-641388F006C4', N'170501145839001', N'000-0001', 0, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'4687EC4D-4129-4554-8FDC-B5C7E448EC27', N'170501194622004', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'4CA0B9E6-F467-45B4-9121-2C9A864F7C4B', N'170501194623012', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'57155918-77A3-4C53-A1FB-AFC3E90AC4F1', N'170501194622002', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'749229A2-9F3C-4F08-A284-4C93EC14BB7D', N'170501153943001', N'000-0002', 1, 1)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'77153480-167E-4B99-A939-34562973B450', N'170501194623010', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'8D6BBC15-9A7F-4E2C-A596-F4775DF63C76', N'170501154122002', N'000-0001', 0, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'9641F81C-5291-40D7-ADC3-3203E42DA05B', N'170501153105001', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'9F1DF89D-10BE-452D-8107-FED5A95E1526', N'170501152120001', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'B8877923-3CA8-4577-B48C-579228E7AA44', N'170501152120003', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'BF355DCD-3B8A-4441-8DE2-2512A32230A9', N'170501194623006', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'C7FBDD46-66FA-4721-99A1-9E2931ADAE5E', N'170501153251001', N'000-0001', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'CBDB0E46-2771-45EC-9D5D-4595FC613863', N'170501194623015', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'D0443CC3-B7C0-4B6D-838D-5F716CD655FC', N'170501194623005', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'D9BABD06-B17F-405C-92CC-6F2EB79CCCAB', N'170501194623008', N'000-0002', 1, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'DDCC9C7F-9B6E-478F-930C-BF055EF138BF', N'170501154122001', N'000-0001', 0, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'E14C2C77-73EC-4D8A-92CD-4A4CC7486FCD', N'170501153207001', N'000-0001', 0, 0)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'FBD4119B-03FF-430B-AA90-60754C04E515', N'170501194623011', N'000-0002', 1, 1)
INSERT [dbo].[Copies] ([Cop_Id], [Cop_No], [Book_ISBN], [Cop_Status], [Cop_isDeleted]) VALUES (N'FF334202-C2A3-4F78-ADD3-1CCFD82AC269', N'170501194623009', N'000-0002', 1, 1)
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'0403B56F-D4B1-4D13-8B1B-370C9A76E620', N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'8D6BBC15-9A7F-4E2C-A596-F4775DF63C76', 1, CAST(N'2017-05-03T17:48:20.007' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'22C2A5AF-74A1-4C10-A21E-803E6FEEF599', N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'36BD2423-AE68-4447-AAD9-641388F006C4', 1, CAST(N'2017-05-03T17:48:20.007' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'28778973-16FF-4A68-9699-37271215145E', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'16E9157C-0386-43E3-9491-DC7B1CC95739', 1, CAST(N'2017-05-05T18:38:12.883' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'2C17E8BC-46D5-4AD7-AFE8-BA7B41FA1F6C', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'D9BABD06-B17F-405C-92CC-6F2EB79CCCAB', 1, CAST(N'2017-05-05T18:38:12.883' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'5D14918A-B7D5-454D-9A52-9EC33BAB4413', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'8D6BBC15-9A7F-4E2C-A596-F4775DF63C76', 0, NULL)
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'5ED4DD1B-B63F-4337-B969-0DEF9E4C3B3E', N'4599D7C3-642A-4B96-8FE9-C2D92B481A46', N'E14C2C77-73EC-4D8A-92CD-4A4CC7486FCD', 0, NULL)
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'624C63A9-F66D-4014-A5F4-E9F037A79D0A', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'DDCC9C7F-9B6E-478F-930C-BF055EF138BF', 0, NULL)
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'AA9C186C-2AC0-4186-9CF2-72ACAACEBD02', N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'D9BABD06-B17F-405C-92CC-6F2EB79CCCAB', 1, CAST(N'2017-05-03T17:48:20.007' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'DBB53B31-453B-4893-AD8D-DE9F3ED3505B', N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'4687EC4D-4129-4554-8FDC-B5C7E448EC27', 1, CAST(N'2017-05-03T17:48:20.007' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'F197710B-8D2A-47A2-83B4-3A5F301AAEB9', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'04883B66-01B3-42EC-A8BE-935F0F9DBD71', 1, CAST(N'2017-05-05T18:38:12.883' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'F389BC62-FB20-48A3-9096-04073AC0CB26', N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'15494F12-3082-4D3F-8064-164BD1DE09E0', 1, CAST(N'2017-05-03T17:48:20.007' AS DateTime))
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'F733F68A-EB1A-4DB1-826E-1409EF6BB03E', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'338DE930-2522-45F3-B111-97344916401E', 0, NULL)
INSERT [dbo].[IRBookDetails] ([IRBookDetail_Id], [IRBook_Id], [Cop_Id], [IRBookDetail_Status], [IRBookDetail_ReturnDate]) VALUES (N'FA1D62EB-2A75-40D8-B299-095C8DC96B81', N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'36BD2423-AE68-4447-AAD9-641388F006C4', 0, NULL)
INSERT [dbo].[IRBooks] ([IRBook_Id], [Mem_Id], [IRBook_CreateDate], [IRBook_DueDate]) VALUES (N'0829E5F8-6C50-4A8F-8764-AFCBFFD0201F', N'26E27EDE-38EE-45A0-924E-4122AEAF296D', CAST(N'2017-05-01T21:44:13.093' AS DateTime), CAST(N'2017-05-08T21:44:13.093' AS DateTime))
INSERT [dbo].[IRBooks] ([IRBook_Id], [Mem_Id], [IRBook_CreateDate], [IRBook_DueDate]) VALUES (N'4599D7C3-642A-4B96-8FE9-C2D92B481A46', N'7B5FE4D0-226A-48D8-BA5F-B032222885EA', CAST(N'2017-05-06T19:11:42.447' AS DateTime), CAST(N'2017-05-11T19:11:42.447' AS DateTime))
INSERT [dbo].[IRBooks] ([IRBook_Id], [Mem_Id], [IRBook_CreateDate], [IRBook_DueDate]) VALUES (N'AB4F3A9B-24A2-46CB-A006-45F26984936A', N'7B5FE4D0-226A-48D8-BA5F-B032222885EA', CAST(N'2017-05-05T18:37:54.517' AS DateTime), CAST(N'2017-05-10T18:37:54.517' AS DateTime))
INSERT [dbo].[Members] ([Mem_Id], [Mem_FirstName], [Mem_LastName], [Mem_No], [Mem_Phone], [Mem_Address], [Mem_Dep], [Mem_Status], [Mem_CreateDate], [Mem_isDelete], [Mem_ImageFile]) VALUES (N'26E27EDE-38EE-45A0-924E-4122AEAF296D', N'Nguyen Van', N'A', N'M0000002', N'1331685616', N'Anywhere', N'Library', 1, CAST(N'2017-04-30T17:57:07.610' AS DateTime), 0, N'imgMem/20170430203240_DH.jpg')
INSERT [dbo].[Members] ([Mem_Id], [Mem_FirstName], [Mem_LastName], [Mem_No], [Mem_Phone], [Mem_Address], [Mem_Dep], [Mem_Status], [Mem_CreateDate], [Mem_isDelete], [Mem_ImageFile]) VALUES (N'310FFC2F-490F-48BB-BA09-4396759DC99B', N'Hoang Thi', N'Nguyet', N'M0000003', N'2122', N'HCM', N'IT', 1, CAST(N'2017-04-30T20:41:37.890' AS DateTime), 0, N'imgMem/20170430214202_Config.jpg')
INSERT [dbo].[Members] ([Mem_Id], [Mem_FirstName], [Mem_LastName], [Mem_No], [Mem_Phone], [Mem_Address], [Mem_Dep], [Mem_Status], [Mem_CreateDate], [Mem_isDelete], [Mem_ImageFile]) VALUES (N'7B5FE4D0-226A-48D8-BA5F-B032222885EA', N'Nguyen Duc', N'Tung', N'M0000001', N'0936655155', N'Hanoi', N'Admin', 1, CAST(N'2017-04-30T17:23:22.663' AS DateTime), 0, N'imgMem/20170430205802_DS.jpg')
INSERT [dbo].[Members] ([Mem_Id], [Mem_FirstName], [Mem_LastName], [Mem_No], [Mem_Phone], [Mem_Address], [Mem_Dep], [Mem_Status], [Mem_CreateDate], [Mem_isDelete], [Mem_ImageFile]) VALUES (N'A201AD95-B5EE-4DA2-B7C5-5D4C5A3E8397', N'Nguyen Duc', N'Tung', N'M0000004', N'13151563', N'HN', N'Finance', 0, CAST(N'2017-04-30T20:54:22.227' AS DateTime), 1, N'imgMem/20170430205422_DS.jpg')
INSERT [dbo].[Members] ([Mem_Id], [Mem_FirstName], [Mem_LastName], [Mem_No], [Mem_Phone], [Mem_Address], [Mem_Dep], [Mem_Status], [Mem_CreateDate], [Mem_isDelete], [Mem_ImageFile]) VALUES (N'B06DC5F9-6A9E-4C70-98BD-045C9F347CA4', N'Hoang Van', N'Tung', N'M0000005', N'21222', N'HN', N'IT', 0, CAST(N'2017-04-30T22:24:32.290' AS DateTime), 0, N'imgMem/MemNoAvatar.png')
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__Categori__B46D3EC31338C44F]    Script Date: 5/7/2017 11:41:13 AM ******/
ALTER TABLE [dbo].[Categories] ADD  CONSTRAINT [UQ__Categori__B46D3EC31338C44F] UNIQUE NONCLUSTERED 
(
	[Cat_Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__Copies__D0CC2A0E26CDC971]    Script Date: 5/7/2017 11:41:13 AM ******/
ALTER TABLE [dbo].[Copies] ADD  CONSTRAINT [UQ__Copies__D0CC2A0E26CDC971] UNIQUE NONCLUSTERED 
(
	[Cop_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__Members__816F186D78C2DBD2]    Script Date: 5/7/2017 11:41:13 AM ******/
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [UQ__Members__816F186D78C2DBD2] UNIQUE NONCLUSTERED 
(
	[Mem_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Books] ADD  CONSTRAINT [DF__Books__Book_Cont__35BCFE0A]  DEFAULT ('') FOR [Book_Content]
GO
ALTER TABLE [dbo].[Books] ADD  CONSTRAINT [DF__Books__Book_Imag__36B12243]  DEFAULT ('/image/nocover.png') FOR [Book_ImageFile]
GO
ALTER TABLE [dbo].[Books] ADD  CONSTRAINT [DF__Books__Book_Crea__37A5467C]  DEFAULT (getdate()) FOR [Book_CreateDate]
GO
ALTER TABLE [dbo].[Books] ADD  CONSTRAINT [DF__Books__Book_isDe__38996AB5]  DEFAULT ((0)) FOR [Book_isDeleted]
GO
ALTER TABLE [dbo].[Categories] ADD  CONSTRAINT [DF__Categorie__Cat_I__398D8EEE]  DEFAULT ([dbo].[newCatID]()) FOR [Cat_Id]
GO
ALTER TABLE [dbo].[Categories] ADD  CONSTRAINT [DF__Categorie__Cat_i__3A81B327]  DEFAULT ((0)) FOR [Cat_isDelete]
GO
ALTER TABLE [dbo].[Copies] ADD  CONSTRAINT [DF__Copies__Cop_Id__3B75D760]  DEFAULT (newid()) FOR [Cop_Id]
GO
ALTER TABLE [dbo].[Copies] ADD  CONSTRAINT [DF__Copies__Cop_Stat__3C69FB99]  DEFAULT ((1)) FOR [Cop_Status]
GO
ALTER TABLE [dbo].[Copies] ADD  CONSTRAINT [DF__Copies__Cop_isDe__3D5E1FD2]  DEFAULT ((0)) FOR [Cop_isDeleted]
GO
ALTER TABLE [dbo].[Fines] ADD  CONSTRAINT [DF__Fines__Fine_Id__3E52440B]  DEFAULT (newid()) FOR [Fine_Id]
GO
ALTER TABLE [dbo].[Fines] ADD  CONSTRAINT [DF_Fines_Fine_PaidDate]  DEFAULT (getdate()) FOR [Fine_PaidDate]
GO
ALTER TABLE [dbo].[Fines] ADD  CONSTRAINT [DF__Fines__Fine_Stat__403A8C7D]  DEFAULT ((0)) FOR [Fine_Status]
GO
ALTER TABLE [dbo].[IRBookDetails] ADD  DEFAULT (newid()) FOR [IRBookDetail_Id]
GO
ALTER TABLE [dbo].[IRBookDetails] ADD  DEFAULT ((0)) FOR [IRBookDetail_Status]
GO
ALTER TABLE [dbo].[IRBooks] ADD  DEFAULT (newid()) FOR [IRBook_Id]
GO
ALTER TABLE [dbo].[IRBooks] ADD  DEFAULT (getdate()) FOR [IRBook_CreateDate]
GO
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [DF__Members__Mem_Id__44FF419A]  DEFAULT (newid()) FOR [Mem_Id]
GO
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [DF__Members__Mem_Sta__45F365D3]  DEFAULT ((1)) FOR [Mem_Status]
GO
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [DF__Members__Mem_Cre__46E78A0C]  DEFAULT (getdate()) FOR [Mem_CreateDate]
GO
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [DF__Members__Mem_isD__47DBAE45]  DEFAULT ((0)) FOR [Mem_isDelete]
GO
ALTER TABLE [dbo].[Members] ADD  CONSTRAINT [DF__Members__Mem_Ima__48CFD27E]  DEFAULT ('/image/noavatar.png') FOR [Mem_ImageFile]
GO
ALTER TABLE [dbo].[Books]  WITH CHECK ADD  CONSTRAINT [FK__Books__Cat_Id__49C3F6B7] FOREIGN KEY([Cat_Id])
REFERENCES [dbo].[Categories] ([Cat_Id])
GO
ALTER TABLE [dbo].[Books] CHECK CONSTRAINT [FK__Books__Cat_Id__49C3F6B7]
GO
ALTER TABLE [dbo].[Books]  WITH CHECK ADD  CONSTRAINT [FK__Books__Cat_Id__7A672E12] FOREIGN KEY([Cat_Id])
REFERENCES [dbo].[Categories] ([Cat_Id])
GO
ALTER TABLE [dbo].[Books] CHECK CONSTRAINT [FK__Books__Cat_Id__7A672E12]
GO
ALTER TABLE [dbo].[Copies]  WITH CHECK ADD  CONSTRAINT [FK__Copies__Book_ISB__4AB81AF0] FOREIGN KEY([Book_ISBN])
REFERENCES [dbo].[Books] ([Book_ISBN])
GO
ALTER TABLE [dbo].[Copies] CHECK CONSTRAINT [FK__Copies__Book_ISB__4AB81AF0]
GO
ALTER TABLE [dbo].[Copies]  WITH CHECK ADD  CONSTRAINT [FK__Copies__Book_ISB__7B5B524B] FOREIGN KEY([Book_ISBN])
REFERENCES [dbo].[Books] ([Book_ISBN])
GO
ALTER TABLE [dbo].[Copies] CHECK CONSTRAINT [FK__Copies__Book_ISB__7B5B524B]
GO
ALTER TABLE [dbo].[Fines]  WITH CHECK ADD  CONSTRAINT [FK__Fines__IRBookDet__4BAC3F29] FOREIGN KEY([IRBookDetail_Id])
REFERENCES [dbo].[IRBookDetails] ([IRBookDetail_Id])
GO
ALTER TABLE [dbo].[Fines] CHECK CONSTRAINT [FK__Fines__IRBookDet__4BAC3F29]
GO
ALTER TABLE [dbo].[Fines]  WITH CHECK ADD  CONSTRAINT [FK__Fines__IRBookDet__7C4F7684] FOREIGN KEY([IRBookDetail_Id])
REFERENCES [dbo].[IRBookDetails] ([IRBookDetail_Id])
GO
ALTER TABLE [dbo].[Fines] CHECK CONSTRAINT [FK__Fines__IRBookDet__7C4F7684]
GO
ALTER TABLE [dbo].[IRBookDetails]  WITH CHECK ADD  CONSTRAINT [FK__IRBookDet__Cop_I__4CA06362] FOREIGN KEY([Cop_Id])
REFERENCES [dbo].[Copies] ([Cop_Id])
GO
ALTER TABLE [dbo].[IRBookDetails] CHECK CONSTRAINT [FK__IRBookDet__Cop_I__4CA06362]
GO
ALTER TABLE [dbo].[IRBookDetails]  WITH CHECK ADD  CONSTRAINT [FK__IRBookDet__Cop_I__7D439ABD] FOREIGN KEY([Cop_Id])
REFERENCES [dbo].[Copies] ([Cop_Id])
GO
ALTER TABLE [dbo].[IRBookDetails] CHECK CONSTRAINT [FK__IRBookDet__Cop_I__7D439ABD]
GO
ALTER TABLE [dbo].[IRBookDetails]  WITH CHECK ADD FOREIGN KEY([IRBook_Id])
REFERENCES [dbo].[IRBooks] ([IRBook_Id])
GO
ALTER TABLE [dbo].[IRBookDetails]  WITH CHECK ADD FOREIGN KEY([IRBook_Id])
REFERENCES [dbo].[IRBooks] ([IRBook_Id])
GO
ALTER TABLE [dbo].[IRBooks]  WITH CHECK ADD  CONSTRAINT [FK__IRBooks__Mem_Id__4E88ABD4] FOREIGN KEY([Mem_Id])
REFERENCES [dbo].[Members] ([Mem_Id])
GO
ALTER TABLE [dbo].[IRBooks] CHECK CONSTRAINT [FK__IRBooks__Mem_Id__4E88ABD4]
GO
ALTER TABLE [dbo].[IRBooks]  WITH CHECK ADD  CONSTRAINT [FK__IRBooks__Mem_Id__7F2BE32F] FOREIGN KEY([Mem_Id])
REFERENCES [dbo].[Members] ([Mem_Id])
GO
ALTER TABLE [dbo].[IRBooks] CHECK CONSTRAINT [FK__IRBooks__Mem_Id__7F2BE32F]
GO
/****** Object:  StoredProcedure [dbo].[Books_DeleteBookByISBN]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_DeleteBookByISBN]
	@Book_ISBN VARCHAR (13)
AS
 BEGIN
 IF EXISTS (SELECT * FROM Copies WHERE Book_ISBN = @Book_ISBN AND Cop_Status = 0)
  RETURN 0;
 begin transaction
 begin try  
	UPDATE Copies 
	SET Cop_isDeleted = 1 
	WHERE Book_ISBN = @Book_ISBN
	
	UPDATE Books
	SET Book_isDeleted = 1
	WHERE Book_ISBN = @Book_ISBN
	
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
   END



GO
/****** Object:  StoredProcedure [dbo].[Books_getBookListByCatename]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_getBookListByCatename]
@Cat_Name NVARCHAR(30)
AS
BEGIN      
	SELECT [Book_ISBN]
	  ,[Book_Title] AS 'Title'
      ,[Book_Author] AS 'Author'
	  ,[Book_Publisher] AS 'Publisher'
	  ,[Book_Language] AS 'Language'
	  ,Book_Price AS 'Price'
      ,CASE WHEN [Book_isDeleted] = 0 THEN 'Active' ELSE 'Deleted' end AS 'Status'     
  FROM view_Book_Cate_Copy
	WHERE Cat_Name LIKE (CASE WHEN @Cat_Name  = '' THEN Cat_Name ELSE '%'+@Cat_Name+'%' END) 
	GROUP BY Book_ISBN, Book_Title, Book_Author, Book_Publisher, Book_isDeleted,Book_Price,[Book_Publisher],[Book_Language]
END



GO
/****** Object:  StoredProcedure [dbo].[Books_getByISBN]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_getByISBN]
@Book_ISBN varchar(8)
AS
BEGIN
	SELECT b.[Book_ISBN]
      ,b.[Book_Title]
      ,b.[Book_Publisher]
      ,b.[Book_Author]
      ,b.[Book_Price]
      ,b.[Book_Content]
      ,b.[Cat_Id]
      ,b.[Book_Language]
      ,b.[Book_ImageFile]
      ,b.[Book_CreateDate]
      ,b.[Book_isDeleted]
      ,bcc.[Cat_Name]
      , COUNT(BCC.Cop_Id) 'Book_Count'
	FROM Books B LEFT JOIN view_Book_Cate_Copy BCC
		ON B.Book_ISBN = BCC.Book_ISBN
	WHERE 
			B.Book_ISBN LIKE (CASE WHEN @Book_ISBN  = '' THEN B.Book_ISBN ELSE '%'+@Book_ISBN+'%' END) AND BCC.Cop_isDeleted=0
	GROUP BY b.[Book_ISBN]
		,b.[Book_Title]
		,b.[Book_Publisher]
		,b.[Book_Author]
		,b.[Book_Price]
		,b.[Book_Content]
		,b.[Cat_Id]
		,b.[Book_Language]
		,b.[Book_ImageFile]
		,b.[Book_CreateDate]
		,b.[Book_isDeleted]
		,BCC.Cat_Name
		
END


GO
/****** Object:  StoredProcedure [dbo].[Books_getByISBNFree]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_getByISBNFree]
@Book_ISBN varchar(8)
AS
BEGIN
	SELECT b.[Book_ISBN]
      ,b.[Book_Title]
      ,b.[Book_Publisher]
      ,b.[Book_Author]
      ,b.[Book_Price]
      ,b.[Book_Content]
      ,b.[Cat_Id]
      ,b.[Book_Language]
      ,b.[Book_ImageFile]
      ,b.[Book_CreateDate]
      ,b.[Book_isDeleted]
      ,bcc.[Cat_Name]
      , COUNT(case BCC.Cop_Status when '1' then 1 else null end) AS 'Book_Count'
	FROM Books B LEFT JOIN view_Book_Cate_Copy BCC
		ON B.Book_ISBN = BCC.Book_ISBN
	WHERE 
			B.Book_ISBN LIKE (CASE WHEN @Book_ISBN  = '' THEN B.Book_ISBN ELSE '%'+@Book_ISBN+'%' END) AND BCC.Cop_isDeleted=0
	GROUP BY b.[Book_ISBN]
		,b.[Book_Title]
		,b.[Book_Publisher]
		,b.[Book_Author]
		,b.[Book_Price]
		,b.[Book_Content]
		,b.[Cat_Id]
		,b.[Book_Language]
		,b.[Book_ImageFile]
		,b.[Book_CreateDate]
		,b.[Book_isDeleted]
		,BCC.Cat_Name
		
END


GO
/****** Object:  StoredProcedure [dbo].[Books_getListBookByFilter]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	get list book by filter
-- =============================================
CREATE PROCEDURE [dbo].[Books_getListBookByFilter]
@Book_ISBN varchar(8) = ''
,@Book_Title nvarchar(100) = ''
,@Book_Author nvarchar(50) = ''
AS
BEGIN
		SELECT 
[Book_ISBN] AS 'ISBN'
	  ,[Book_Title] AS 'Title'
      ,[Book_Author] AS 'Author'
	  ,[Book_Publisher] AS 'Publisher'
	  ,[Book_Language] AS 'Language'
	  ,Book_Price AS 'Price'
      ,CASE WHEN [Book_isDeleted] = 0 THEN 'Active' ELSE 'Deleted' end AS 'Status' 
		FROM view_Book_Cate_Copy B
		
		WHERE B.[Book_ISBN] LIKE (CASE WHEN @Book_ISBN = '' THEN B.[Book_ISBN] ELSE '%'+@Book_ISBN+'%' END)
			AND	B.Book_Title LIKE (CASE WHEN @Book_Title = '' THEN B.Book_Title ELSE '%'+@Book_Title+'%' END)
			AND	B.Book_Author like (CASE WHEN @Book_Author = '' THEN B.Book_Author ELSE '%'+@Book_Author+'%' END)
			AND Book_isDeleted = 0
		group by B.[Book_ISBN] 
					,B.[Book_Title] 
					,B.Book_Author ,Book_Price,[Book_Publisher],[Book_Language],Book_isDeleted

END



GO
/****** Object:  StoredProcedure [dbo].[Books_Insert]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_Insert]
	@Book_ISBN VARCHAR(13),
	@Book_Title nvarchar(50),
	@Book_Publisher nvarchar(50),
	@Book_Author nvarchar(50),
	@Book_Price float,
	@Book_Content nvarchar(1000),
	@Cat_Id char(36),
	@Book_Language varchar(7),
	@Book_ImageFile varchar(255),
	@Book_isDelete BIT
AS
 BEGIN
 begin transaction
 begin try  
   INSERT INTO [dbo].[Books]
           ([Book_ISBN]
           ,[Book_Title]
           ,[Book_Publisher]
           ,[Book_Author]
           ,[Book_Price]
           ,[Book_Content]
           ,[Cat_Id]
           ,[Book_Language]
           ,[Book_ImageFile]
           ,[Book_isDeleted])
     VALUES	 
           (@Book_ISBN,
           	@Book_Title,
			@Book_Publisher,
			@Book_Author,
			@Book_Price,
			@Book_Content,
			@Cat_Id,
			@Book_Language,
			@Book_ImageFile,
			@Book_isDelete)
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END

 DELETE FROM Books
 SELECT * FROM Books b


GO
/****** Object:  StoredProcedure [dbo].[Books_searchBook]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_searchBook]
@Book_Title nvarchar(50) = NULL
,@Book_Publisher nvarchar(50) = NULL
,@Book_Author nvarchar(50) = NULL
,@Book_isDeleted CHAR = '-1'
AS
BEGIN
SELECT [Book_ISBN]
		,[Book_Title] AS 'Title'
      ,[Book_Author] AS 'Author'
      ,[Book_Publisher] AS 'Publisher'
      ,CASE WHEN [Book_isDeleted] = 0 THEN 'Active' ELSE 'Inactive' end AS 'Status'
  FROM Books
WHERE 
	Book_Title LIKE (CASE WHEN @Book_Title='' OR @Book_Title IS NULL THEN Book_Title ELSE '%'+@Book_Title+'%' END)
	AND Book_Author LIKE (CASE WHEN @Book_Author='' OR @Book_Author IS NULL THEN Book_Author ELSE '%'+@Book_Author+'%' END)
	AND Book_Publisher LIKE (CASE WHEN @Book_Publisher='' OR @Book_Publisher IS NULL THEN Book_Publisher ELSE '%'+@Book_Publisher+'%' END)
	AND Book_isDeleted = (CASE WHEN @Book_isDeleted = '-1' THEN Book_isDeleted ELSE @Book_isDeleted END)
END



GO
/****** Object:  StoredProcedure [dbo].[Books_Update]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Books_Update]
	@Book_ISBN VARCHAR(13),
	@Book_Title nvarchar(50),
	@Book_Publisher nvarchar(50),
	@Book_Author nvarchar(50),
	@Book_Price float,
	@Book_Content nvarchar(1000),
	@Cat_Id char(36),
	@Book_Language varchar(7),
	@Book_ImageFile varchar(255),
	@Book_isDelete BIT
AS
 BEGIN
 begin transaction
 BEGIN TRY
	UPDATE Books
	   SET 
		  [Book_Title] = @Book_Title
           ,[Book_Publisher] = @Book_Publisher
           ,[Book_Author] = @Book_Author
           ,[Book_Price] = @Book_Price
           ,[Book_Content] = @Book_Content
           ,[Cat_Id] = @Cat_Id
           ,[Book_Language] = @Book_Language
           ,[Book_ImageFile] = @Book_ImageFile
           ,[Book_isDeleted] = @Book_isDelete
	 WHERE [Book_ISBN] = @Book_ISBN
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END

 DELETE FROM Books
 SELECT * FROM Books b


GO
/****** Object:  StoredProcedure [dbo].[Categories_getCategoryByCateId]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[Categories_getCategoryByCateId] 
	@Cat_Id NVARCHAR(36)
AS
BEGIN
	SELECT * FROM Categories c WHERE c.Cat_Id = @Cat_Id
END



GO
/****** Object:  StoredProcedure [dbo].[Categories_getCategoryByCateName]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_getCategoryByCateName] 
	@Cat_Name NVARCHAR(36)
AS
BEGIN
	SELECT * FROM Categories c WHERE c.Cat_Name = @Cat_Name
END



GO
/****** Object:  StoredProcedure [dbo].[Categories_getCategoryList]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_getCategoryList]
AS
BEGIN
	SELECT c.Cat_Name AS 'Category'
		FROM Categories c
		WHERE c.Cat_isDelete = 0	--Cat_IsDeleted = 0 (khong bi xoa) - 1 (xoa)
		ORDER BY c.Cat_Id
END



GO
/****** Object:  StoredProcedure [dbo].[Categories_getCategoryListWithBookNumber]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_getCategoryListWithBookNumber]
AS
	BEGIN
		SELECT c.Cat_Id AS 'Category ID', c.Cat_Name AS 'Category', isnull(COUNT(b.Book_ISBN),0) AS 'Book Count'
		FROM Categories c
		LEFT JOIN Books b ON c.Cat_Id = b.Cat_Id
		WHERE c.Cat_isDelete = 0	--Cat_IsDeleted = 0 (khong bi xoa) - 1 (xoa)
		GROUP BY c.Cat_Id, c.Cat_Name
		ORDER BY c.Cat_Id
	END



GO
/****** Object:  StoredProcedure [dbo].[Categories_Insert]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_Insert]
@Cat_Name NVARCHAR(30),
@Cat_isDelete BIT,
@Cat_Description NVARCHAR(100)
AS
 BEGIN
 IF EXISTS (SELECT * FROM Categories WHERE Cat_Name = @Cat_Name)
  RETURN 0;
 begin transaction
 begin try  
   INSERT INTO [dbo].[Categories]
     ([Cat_Name]
     ,[Cat_isDelete]
     ,[Cat_Description])
  VALUES
     (@Cat_Name
     , @Cat_isDelete
     , @Cat_Description)
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END


GO
/****** Object:  StoredProcedure [dbo].[Categories_Lock]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_Lock]
@Cat_Id NVARCHAR(3)
AS
	BEGIN
 begin transaction
 DECLARE @num int
 Select @num =COUNT (*) FROM view_Book_Cate_Copy WHERE Cat_Id=@Cat_Id AND Book_isDeleted=0
 if @num>0 return 2 
 begin try  
  UPDATE [dbo].[Categories]
		   SET
			  [Cat_isDelete] = 1
		WHERE Cat_Id = @Cat_Id
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
   END


GO
/****** Object:  StoredProcedure [dbo].[Categories_Update]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Categories_Update]
	@Cat_Id NVARCHAR(36),
	@Cat_Name NVARCHAR(30),
	@Cat_isDelete BIT,
	@Cat_Description NVARCHAR(100)
AS
 BEGIN
 IF EXISTS (SELECT * FROM Categories WHERE Cat_Name = @Cat_Name)
  RETURN 0;
 begin transaction
 begin try  
   UPDATE [dbo].[Categories]
	   SET 
		  [Cat_Name] = @Cat_Name
		  ,[Cat_isDelete] = @Cat_isDelete
		  ,[Cat_Description] = @Cat_Description
	 WHERE Cat_Id = @Cat_Id
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
END


GO
/****** Object:  StoredProcedure [dbo].[Copies_DeleteCopiesByCopyNo]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[Copies_DeleteCopiesByCopyNo]
	@Cop_Id VARCHAR (50)
AS
 BEGIN
 IF EXISTS (SELECT * FROM Copies WHERE Cop_Id = @Cop_Id AND Cop_Status = 0)
  RETURN 0;
 begin transaction
 begin try  
	UPDATE Copies 
	SET Cop_isDeleted = 1
	WHERE Cop_Id = @Cop_Id
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
   END



GO
/****** Object:  StoredProcedure [dbo].[Copies_DeleteCopiesByISBN]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[Copies_DeleteCopiesByISBN]
	@Book_ISBN VARCHAR (13)
AS
 BEGIN
 IF EXISTS (SELECT * FROM Copies WHERE Book_ISBN = @Book_ISBN AND Cop_Status = 1)
  RETURN 0;
 begin transaction
 begin try  
	UPDATE Copies 
	SET Cop_isDeleted = 1 
	WHERE Book_ISBN = @Book_ISBN
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
   END



GO
/****** Object:  StoredProcedure [dbo].[Copies_getFreeCopiesList]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	get lastest copy which is free
-- =============================================
CREATE PROCEDURE [dbo].[Copies_getFreeCopiesList]
@Book_ISBN varchar(13)
AS
BEGIN
		SELECT [Cop_Id]
		,Book_ISBN as 'ISBN'
      ,[Book_Title] as 'Title'
      ,[Book_Author] as 'Author'	  
      ,[Cop_No] as 'Copy No'
		FROM view_Book_Cate_Copy
		WHERE [Book_ISBN] like (CASE WHEN @Book_ISBN='' THEN [Book_ISBN] ELSE '%'+@Book_ISBN+'%' END)AND
			 [Cop_isDeleted] = 0
END


GO
/****** Object:  StoredProcedure [dbo].[Copies_getLastestIsFree]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	get lastest copy which is free
-- =============================================
CREATE PROCEDURE [dbo].[Copies_getLastestIsFree]
@Book_ISBN varchar(13)
,@Cop_IdList varchar(500)
AS
BEGIN
		SELECT TOP(1) Cop_No ,Cop_Id,Book_ISBN,Cop_Status,Cop_isDeleted
		FROM [Copies] 
		WHERE [Book_ISBN] = @Book_ISBN
			AND [Cop_Status] = 1
			AND [Cop_isDeleted] = 0
			AND Cop_Id NOT IN ( SELECT ITEMS FROM Split(@Cop_IdList,','))
		order by NEWID()
END


GO
/****** Object:  StoredProcedure [dbo].[Copies_getNumberOfCopies]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	get lastest copy which is free
-- =============================================
CREATE PROCEDURE [dbo].[Copies_getNumberOfCopies]
AS
SELECT [Cop_Id]
      ,[Cop_No]
      ,[Book_ISBN]
      ,[Cop_Status]
      ,[Cop_isDeleted]
  FROM [Copies]
		WHERE [Cop_isDeleted]=0



GO
/****** Object:  StoredProcedure [dbo].[Copies_Insert]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE [dbo].[Copies_Insert]
	@Book_ISBN VARCHAR (13)
	,@Cop_No CHAR (15)
AS
 BEGIN
 begin transaction
 begin try  
INSERT INTO [dbo].[Copies]
           ([Cop_No]
           ,[Book_ISBN]
           ,[Cop_Status]
           ,[Cop_isDeleted])
     VALUES
           (@Cop_No
           ,@Book_ISBN
           ,1
           ,0)
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END



GO
/****** Object:  StoredProcedure [dbo].[Fines_ListByMemberNo]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	paid fee fine
-- =============================================
CREATE PROCEDURE [dbo].[Fines_ListByMemberNo]
@Mem_No char(8)

--,@Fine_Id char(36)
AS
BEGIN
	SELECT 
	Row_number() over (order by [Fine_PaidDate]) 'No'
	,[Fine_Id] 'ID'
      ,bcc.Book_ISBN 'ISBN'
	    ,bcc.Book_Title 'Title'
		,bcc.Cop_No 'Copy No'
		,bcc.Book_Author 'Author'
      ,[Fine_Amount] 'Money'
     
      
   
   
	FROM Fines f LEFT JOIN [dbo].[view_IR_IRDetail_Member] irm
	ON irm.IRBookDetail_id = f.IRBookDetail_Id LEFT JOIN [dbo].[view_Book_Cate_Copy] bcc
	ON bcc.Cop_Id = irm.Cop_Id
	WHERE [Fine_Status] = 0
	AND Mem_No = @Mem_No
END



GO
/****** Object:  StoredProcedure [dbo].[Fines_Paid]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	paid fee fine
-- =============================================
CREATE PROCEDURE [dbo].[Fines_Paid]
@IRBookDetail_Id varchar(500)
,@FineAmmount float
AS
BEGIN
	BEGIN TRAN
		BEGIN TRY			
			INSERT INTO [dbo].[Fines]
			([IRBookDetail_Id]
      ,[Fine_Amount]
	  ,[Fine_Status])
	  VALUES
	  (@IRBookDetail_Id,@FineAmmount,0)				
		END TRY
		BEGIN CATCH
		IF @@trancount>0
		ROLLBACK TRAN;
		END CATCH
		IF @@trancount>0
			BEGIN
				COMMIT TRAN;
				RETURN 1; -- THANH CONG
			END
		ELSE
		RETURN 2; -- ERROR 
	
END



GO
/****** Object:  StoredProcedure [dbo].[Fines_REPORT]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	paid fee fine
-- =============================================
CREATE PROCEDURE [dbo].[Fines_REPORT]
@Fine_FROMDATE VARCHAR(10)
,@Fine_TODATE VARCHAR(10)


--,@Fine_Id char(36)
AS
BEGIN
	SELECT 
	Row_number() over (order by [Fine_PaidDate]) 'No'
	--,[Fine_Id] 'ID'
      ,bcc.Book_ISBN 'ISBN'
	    ,bcc.Book_Title 'Title'
		,bcc.Cop_No 'Copy No'
		,bcc.Book_Author 'Author'
      ,[Fine_Amount] 'Money'
	,CASE WHEN Fine_Status = 0 THEN 'UnPaid' else 'Paid' end 'Status' 
	FROM Fines f LEFT JOIN [dbo].[view_IR_IRDetail_Member] irm
	ON irm.IRBookDetail_id = f.IRBookDetail_Id LEFT JOIN [dbo].[view_Book_Cate_Copy] bcc
	ON bcc.Cop_Id = irm.Cop_Id
	--WHERE [Fine_Status] = 0
	--AND Mem_No = @Mem_No
END

GO
/****** Object:  StoredProcedure [dbo].[IRBooks_getBookReturning]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	view all issued book which not return yet
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_getBookReturning]
@IRBookDetail_Id  nvarchar(50)
AS
BEGIN
	SELECT 
	IRM.IRBookDetail_Id 
     ,Row_number() over (order by [IRBook_CreateDate]) 'No'
     ,BCC.Book_ISBN 'ISBN'
     ,BCC.Book_Title 'Title'
		,BCC.Cop_No 'Cop No'
     ,CONVERT(VARCHAR(10), [IRBook_CreateDate],103) 'IssueDate'
      ,CONVERT(VARCHAR(10), [IRBook_DueDate],103) 'DueDate'
	,case when DATEDIFF(DAY,[IRBook_DueDate],GETDATE()) >= 0 THEN 	 DATEDIFF(DAY,[IRBook_DueDate],GETDATE())
						ELSE 0 END 'Late Day'
				
	   FROM [view_IR_IRDetail_Member] IRM 
	   LEFT JOIN [view_Book_Cate_Copy] BCC ON IRM.Cop_Id = BCC.Cop_Id
	   
	   WHERE	IRM.IRBookDetail_Status = 0 
	   AND irm.IRBookDetail_Id  = @IRBookDetail_Id 
END


GO
/****** Object:  StoredProcedure [dbo].[IRBooks_getListBookByMemNo]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	view all issued book which not return yet
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_getListBookByMemNo]
@Mem_No char(8)
AS
BEGIN
	SELECT 
	IRM.IRBookDetail_Id 
     ,Row_number() over (order by [IRBook_CreateDate]) 'No'
     ,BCC.Book_ISBN 'ISBN'
     ,BCC.Book_Title 'Title'
		,BCC.Cop_No 'Cop No'
     ,CONVERT(VARCHAR(10), [IRBook_CreateDate],103) 'IssueDate'
      ,CONVERT(VARCHAR(10), [IRBook_DueDate],103) 'DueDate'
	,case when DATEDIFF(DAY,[IRBook_DueDate],GETDATE()) >= 0 THEN 	 DATEDIFF(DAY,[IRBook_DueDate],GETDATE())
						ELSE 0 END 'Late Day'
	   FROM [view_IR_IRDetail_Member] IRM 
	   LEFT JOIN [view_Book_Cate_Copy] BCC ON IRM.Cop_Id = BCC.Cop_Id
	   
	   WHERE  irm.Mem_No = @Mem_No
END


GO
/****** Object:  StoredProcedure [dbo].[IRBooks_getListBookNotReturn]    Script Date: 5/7/2017 11:41:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	view all issued book which not return yet
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_getListBookNotReturn]
@Mem_No char(8)
AS
BEGIN
	SELECT 
	IRM.IRBookDetail_Id 
     ,Row_number() over (order by [IRBook_CreateDate]) 'No'
     ,BCC.Book_ISBN 'ISBN'
     ,BCC.Book_Title 'Title'
		,BCC.Cop_No 'Cop No'
     ,CONVERT(VARCHAR(10), [IRBook_CreateDate],103) 'IssueDate'
      ,CONVERT(VARCHAR(10), [IRBook_DueDate],103) 'DueDate'
	,case when DATEDIFF(DAY,[IRBook_DueDate],GETDATE()) >= 0 THEN 	 DATEDIFF(DAY,[IRBook_DueDate],GETDATE())
						ELSE 0 END 'Late Day'
	,case when DATEDIFF(DAY,[IRBook_DueDate],GETDATE()) >= 0 THEN 	 0.1*DATEDIFF(DAY,[IRBook_DueDate],GETDATE())
						ELSE 0 END 'Fine ammount'		
	   FROM [view_IR_IRDetail_Member] IRM 
	   LEFT JOIN [view_Book_Cate_Copy] BCC ON IRM.Cop_Id = BCC.Cop_Id
	   
	   WHERE	IRM.IRBookDetail_Status = 0 
	   AND irm.Mem_No = @Mem_No
END


GO
/****** Object:  StoredProcedure [dbo].[IRBooks_getListBookNotReturnByMemberNo]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	view all issued book which not return yet
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_getListBookNotReturnByMemberNo]
@Mem_No char(8)
AS
BEGIN
	SELECT 
	--1 as ' '
     Row_number() over (order by [IRBook_CreateDate]) 'No'
     ,CONVERT(VARCHAR(10), [IRBook_CreateDate],103) 'CreateDate'
      ,CONVERT(VARCHAR(10), [IRBook_DueDate],103) 'DueDate'
		,BCC.Book_ISBN 'ISBN'
		,BCC.Book_Title 'Title'
		,BCC.Cop_No 'Cop No'
		
	   FROM [view_IR_IRDetail_Member] IRM 
	   LEFT JOIN [view_Book_Cate_Copy] BCC ON IRM.Cop_Id = BCC.Cop_Id
	   
	   WHERE	IRM.IRBookDetail_Status = 0 
	   AND irm.Mem_No = @Mem_No
END


GO
/****** Object:  StoredProcedure [dbo].[IRBooks_IssueBook]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: 09/01/2015
-- Description:	ISSUE BOOK
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_IssueBook]
@Cop_No varchar(500)
,@Mem_Id char(36)	
AS

DECLARE @IRBook_Id char(36)
DECLARE @CountTemp int
DECLARE @Cop_Id CHAR(36)
DECLARE @CountCopyID int
BEGIN
	SELECT @Cop_Id = Cop_id FROM Copies WHERE Cop_No = @Cop_No;
	SELECT @CountTemp = COUNT(*) 
			FROM IRBooks IR LEFT JOIN
				IRBookDetails IRD ON IR.IRBook_Id = IRD.IRBook_Id
			WHERE IRD.IRBookDetail_Status = 0 and IR.Mem_Id = @Mem_Id
			GROUP BY IR.MEM_ID
	SELECT @CountCopyID = COUNT(*) FROM dbo.Split(@Cop_No,',')
	IF (@CountTemp + @CountCopyID <= 5 OR @CountTemp IS NULL)	--KIEM TRA CO VUOT QUA SO LUONG MUON CHO PHEP HAY CHUA
	BEGIN
		--EXEC @ReturnValue =  IRBooks_Insert @Mem_Id,@duedate,@IRBook_Id OUTPUT	
		--INSERT IRBooks
		SET @IRBook_Id = NEWID();
		BEGIN TRAN
			BEGIN TRY			
				INSERT INTO [IRBooks]
				   ([IRBook_Id]
				   ,[Mem_Id]				   
				   ,[IRBook_DueDate])
				 VALUES
				   (@IRBook_Id
				   ,@Mem_Id				  
				   ,GETDATE() + 5) -- 5 Days
				 
				INSERT INTO [IRBookDetails]
			   ([IRBookDetail_Id]
			   ,[IRBook_Id]
			   ,[Cop_Id]
			    )			   
				SELECT NEWID(),@IRBook_Id,b.Cop_Id FROM dbo.Split(@Cop_No,',') a LEFT JOIN Copies b
				ON a.items = b.Cop_No				
				
				UPDATE c
					SET c.Cop_Status = 0
				FROM Copies c INNER JOIN dbo.Split(@Cop_No,',') s
				ON s.items = c.Cop_No
				
		END TRY
		BEGIN CATCH
			IF @@trancount>0
			ROLLBACK TRAN;
		END CATCH
		IF @@trancount>0
			BEGIN
				COMMIT TRAN;
				RETURN 1; -- THANH CONG
			END
		ELSE
		RETURN 2; -- ERROR 
	END
	ELSE
		RETURN 0; --ERROR VUOT QUA SO LUONG COPY CHO MUON
		
END


GO
/****** Object:  StoredProcedure [dbo].[IRBooks_ReturnBook]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: 09/01/2015
-- Description:	Return BOOK
-- =============================================
CREATE PROCEDURE [dbo].[IRBooks_ReturnBook]
@IRBookDetail_Id  varchar(500)
--,@Fine_Amount decimal(7,2)
--,@lateday int
AS
BEGIN

	
	--KIEM TRA CO TON TAI RECORD CHUA TRA SACH HAY KHONG??
	BEGIN		
		BEGIN TRAN
			BEGIN TRY			
			UPDATE  IRD	
					SET IRBookDetail_Status = 1
					,IRBookDetail_ReturnDate = GETDATE()
			From  IRBookDetails IRD inner join dbo.Split(@IRBookDetail_Id,',') s
			on s.items = IRD.IRBookDetail_Id			

			UPDATE c
			SET c.Cop_Status = 1
			FROM IRBookDetails ir INNER JOIN Copies c
			ON c.Cop_Id = ir.Cop_Id inner join dbo.Split(@IRBookDetail_Id,',') s
			on s.items = ir.IRBookDetail_Id
			where c.Cop_Status = 0
			
			
			INSERT INTO Fines (IRBookDetail_Id,Fine_Amount)	
			select items,(DATEDIFF(DAY,irm.IRBook_DueDate,GETDATE()) * (bcc.Book_Price/100)) 
			from  dbo.Split(@IRBookDetail_Id,',') s inner join [dbo].[view_IR_IRDetail_Member] irm
			on irm.IRBookDetail_Id = s.items inner join [dbo].[view_Book_Cate_Copy] bcc
			on bcc.Cop_Id = irm.Cop_Id
			where DATEDIFF(DAY,irm.IRBook_DueDate,GETDATE()) > 0
						
			END TRY
		BEGIN CATCH
			IF @@trancount>0
			ROLLBACK TRAN;
		END CATCH
		IF @@trancount>0
			BEGIN
				COMMIT TRAN;
				RETURN 1; -- THANH CONG
			END
		ELSE
		RETURN 2; -- ERROR 
	END
END


GO
/****** Object:  StoredProcedure [dbo].[Members_GetByID]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY ID
-- =============================================
Create PROCEDURE [dbo].[Members_GetByID]
@Mem_Id CHAR(36)
AS
BEGIN
		SELECT [Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,[Mem_CreateDate]
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
  AND Mem_Id = @Mem_Id
  
END




GO
/****** Object:  StoredProcedure [dbo].[Members_GetByNo]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY ID
-- =============================================
CREATE PROCEDURE [dbo].[Members_GetByNo]
@Mem_No CHAR(36)
AS
BEGIN
		SELECT [Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,[Mem_CreateDate]
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
  AND Mem_No = @Mem_No
  
END




GO
/****** Object:  StoredProcedure [dbo].[Members_GetByParameters]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY PARAMETER
-- =============================================
CREATE PROCEDURE [dbo].[Members_GetByParameters]
@Mem_No char(8) = ''
,@Mem_name nvarchar(80) = '' 
,@Mem_Dep varchar(50) = ''
,@Mem_Phone varchar(20) = ''
AS
BEGIN
		SELECT [Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,[Mem_CreateDate]
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
  AND Mem_No = (CASE WHEN @Mem_No  = '' THEN Mem_No ELSE @Mem_No END)
  AND (Mem_FirstName LIKE (CASE WHEN @Mem_name  = '' THEN Mem_FirstName ELSE '%'+ @Mem_name+'%' END) 
			OR	Mem_LastName  LIKE (CASE WHEN @Mem_name  = '' THEN Mem_LastName ELSE '%' +@Mem_name+'%' END))
  AND Mem_Dep like (CASE WHEN @Mem_Dep  = '' THEN Mem_Dep ELSE '%'+@Mem_Dep+'%' END)
  AND Mem_Phone like (CASE WHEN @Mem_Phone  = '' THEN Mem_Phone ELSE '%'+@Mem_Phone+'%' END)
END


GO
/****** Object:  StoredProcedure [dbo].[Members_GetFilterBySearchBox]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY PARAMETER
-- =============================================
CREATE PROCEDURE [dbo].[Members_GetFilterBySearchBox]

@Mem_name nvarchar(80)
,@Mem_No nvarchar(10)
AS
BEGIN
		SELECT [Mem_ID]
		,[Mem_No] AS 'No'
      ,[Mem_FirstName] AS 'First Name'
      ,[Mem_LastName] AS 'Last Name'
      ,[Mem_Phone] AS 'Phone'
      ,[Mem_Address] AS 'Address' 
      ,[Mem_Dep] AS 'Department'
      ,case when Mem_Status = 1 then 'Active' else 'InActive' end AS 'Status'
      --,[Mem_CreateDate] AS 'Date Created'
      --,[Mem_isDelete] AS 'Deleted'
      --,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
    AND ((Mem_FirstName LIKE (CASE WHEN @Mem_name  = '' THEN Mem_FirstName ELSE '%'+@Mem_name+'%' END) 
			OR	Mem_LastName  LIKE (CASE WHEN @Mem_name  = '' THEN Mem_LastName ELSE  '%'+@Mem_name+'%' END))
    AND Mem_No like (CASE WHEN @Mem_No  = '' THEN Mem_No ELSE '%'+@Mem_No+'%' END))
END


GO
/****** Object:  StoredProcedure [dbo].[Members_GetFilterBySearchBox2]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY PARAMETER
-- =============================================
CREATE PROCEDURE [dbo].[Members_GetFilterBySearchBox2]

@Mem_name nvarchar(80)
,@Mem_No nvarchar(10)

AS
BEGIN
		SELECT [Mem_ID]
		,[Mem_No] AS 'No'
      ,[Mem_FirstName] AS 'First Name'
      ,[Mem_LastName] AS 'Last Name'
      ,[Mem_Phone] AS 'Phone'
      ,[Mem_Address] AS 'Address' 
      ,[Mem_Dep] AS 'Department'
      --,case when Mem_Status = 1 then 'Active' else 'InActive' end AS 'Status'
      --,[Mem_CreateDate] AS 'Date Created'
      --,[Mem_isDelete] AS 'Deleted'
      --,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0 AND [Mem_Status]=1
    AND ((Mem_FirstName LIKE (CASE WHEN @Mem_name  = '' THEN Mem_FirstName ELSE '%'+@Mem_name+'%' END) 
			OR	Mem_LastName  LIKE (CASE WHEN @Mem_name  = '' THEN Mem_LastName ELSE  '%'+@Mem_name+'%' END))
    AND Mem_No like (CASE WHEN @Mem_No  = '' THEN Mem_No ELSE '%'+@Mem_No+'%' END))
END


GO
/****** Object:  StoredProcedure [dbo].[Members_GetIRCountInformation]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Members_GetIRCountInformation]
@Mem_No char(8)

AS
BEGIN
	SELECT MEM.[Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,CONVERT(VARCHAR(10),[Mem_CreateDate] ,103) 'Mem_CreateDate'
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
	  ,ISNULL([Book Issued],0) 'Count_Issued'
	   FROM [dbo].[Members] MEM LEFT JOIN 
		  (	  SELECT M.[Mem_Id]
						,COUNT(Cop_Id) 'Book Issued'
			FROM [dbo].[Members] M
			LEFT JOIN IRBooks IR ON IR.Mem_Id = M.Mem_Id
			LEFT JOIN IRBookDetails IRD ON IRD.IRBook_Id = IR.IRBook_Id 
			WHERE IRD.IRBookDetail_Status = 0
			GROUP BY M.[Mem_Id]) TEMP

		ON TEMP.Mem_Id = MEM.Mem_Id
	WHERE Mem_No = @Mem_No
END

GO
/****** Object:  StoredProcedure [dbo].[Members_getMemberByMemberId]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY ID
-- =============================================
Create PROCEDURE [dbo].[Members_getMemberByMemberId]
@Mem_Id CHAR(36)
AS
BEGIN
		SELECT [Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,[Mem_CreateDate]
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
  AND Mem_Id = @Mem_Id
  
END




GO
/****** Object:  StoredProcedure [dbo].[Members_getMemberByMemberNo]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Zito
-- Create date: <Create Date,,>
-- Description:	GET MEMBER BY ID
-- =============================================
CREATE PROCEDURE [dbo].[Members_getMemberByMemberNo]
@Mem_No CHAR(36)
AS
BEGIN
		SELECT [Mem_Id]
      ,[Mem_FirstName]
      ,[Mem_LastName]
      ,[Mem_No]
      ,[Mem_Phone]
      ,[Mem_Address]
      ,[Mem_Dep]
      ,[Mem_Status]
      ,[Mem_CreateDate]
      ,[Mem_isDelete]
      ,[Mem_ImageFile]
  FROM [dbo].[Members]
  WHERE [Mem_isDelete] = 0
  AND Mem_No = @Mem_No
  
END




GO
/****** Object:  StoredProcedure [dbo].[Members_Insert]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Members_Insert]

			@Mem_FirstName nvarchar(30)
           ,@Mem_LastName nvarchar(50)
           ,@Mem_Phone varchar(20)
           ,@Mem_Address nvarchar(200)
           ,@Mem_Dep varchar(50)
           ,@Mem_ImageFile varchar(255)
           

AS
BEGIN
	
	IF EXISTS (SELECT * FROM Members WHERE Mem_Phone = @Mem_Phone)
		RETURN 3;
	BEGIN TRAN
		BEGIN TRY
			INSERT INTO [dbo].[Members]
           ([Mem_FirstName]
           ,[Mem_LastName]
           ,Mem_No
           ,[Mem_Phone]
           ,[Mem_Address]
           ,[Mem_Dep]
           ,[Mem_ImageFile])
     VALUES
           (@Mem_FirstName
           ,@Mem_LastName
           ,dbo.returnNextMemNo()
           ,@Mem_Phone
           ,@Mem_Address
           ,@Mem_Dep
           ,@Mem_ImageFile)
		END TRY
	BEGIN CATCH
		IF @@trancount>0
		ROLLBACK TRAN;
	END CATCH
	IF @@TRANCOUNT>0
		BEGIN
			COMMIT TRAN
			RETURN 1; --SUCCESS
		END
	ELSE
		RETURN 2;--FAIL
END




GO
/****** Object:  StoredProcedure [dbo].[Members_Lock]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[Members_Lock]
      @Mem_Id char(36)
AS
 BEGIN
 begin transaction
 begin try  
   UPDATE [dbo].[Members]
   SET Mem_isDelete = 1, Mem_Status=0
	WHERE Mem_Id = @Mem_Id
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END



GO
/****** Object:  StoredProcedure [dbo].[Members_Update]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Members_Update]
      @Mem_Id char(36)
      ,	@Mem_FirstName nvarchar(30)
      ,	@Mem_LastName nvarchar(50)
      , @Mem_Phone varchar(20)
      , @Mem_Address nvarchar(200)
      ,	@Mem_Dep varchar(50)
      , @Mem_ImageFile varchar(255)
      , @Mem_Status BIT
AS
 BEGIN
 begin transaction
 begin try  
   UPDATE [dbo].[Members]
   SET [Mem_FirstName] = @Mem_FirstName
      ,[Mem_LastName] = @Mem_LastName
      ,[Mem_Phone] = @Mem_Phone
      ,[Mem_Address] = @Mem_Address
      ,[Mem_Dep] = @Mem_Dep
      ,[Mem_ImageFile] = @Mem_ImageFile
      ,Mem_Status = @Mem_Status
	WHERE Mem_Id = @Mem_Id
 end try
 begin catch
  if @@trancount >0
   rollback transaction;
 end catch
  if @@trancount >0
  begin
   commit transaction;
   return 1;
  end
  else
   return 2;
 END



GO
/****** Object:  StoredProcedure [dbo].[Mems_getMemberList]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Mems_getMemberList]
AS
BEGIN
	SELECT [Mem_ID]
		,[Mem_No] AS 'No'
      ,[Mem_FirstName] AS 'First Name'
      ,[Mem_LastName] AS 'Last Name'
      ,[Mem_Phone] AS 'Phone'
      ,[Mem_Address] AS 'Address' 
      ,[Mem_Dep] AS 'Department'
      ,case when Mem_Status = 1 then 'Active' else 'InActive' end AS 'Status'
      --,[Mem_CreateDate] AS 'Date Created'
      --,[Mem_isDelete] AS 'Deleted'
      --,[Mem_ImageFile]
	FROM [dbo].[Members] 
	WHERE [Mem_isDelete] !=1
END

GO
/****** Object:  StoredProcedure [dbo].[Mems_getMemberListByStatus]    Script Date: 5/7/2017 11:41:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Mems_getMemberListByStatus]
@Mem_Status int,
@Mem_Deleted int
AS
BEGIN
	SELECT [Mem_ID]
		,[Mem_No] AS 'No'
      ,[Mem_FirstName] AS 'First Name'
      ,[Mem_LastName] AS 'Last Name'
      ,[Mem_Phone] AS 'Phone'
      ,[Mem_Address] AS 'Address' 
      ,[Mem_Dep] AS 'Department'
      ,case when Mem_Status = 1 then 'Active' else 'InActive' end AS 'Status'
      --,[Mem_CreateDate] AS 'Date Created'
      ,[Mem_isDelete] AS 'Deleted'
      --,[Mem_ImageFile]
	FROM [dbo].[Members] 
	WHERE Mem_Status = (CASE WHEN @Mem_Status=2 THEN Mem_Status ELSE CAST (@Mem_Status AS bit) END) 
	AND Mem_isDelete = (CASE WHEN @Mem_Deleted=2 THEN Mem_isDelete ELSE CAST(@Mem_Deleted AS bit) END)
END

GO
USE [master]
GO
ALTER DATABASE [Sem2Project] SET  READ_WRITE 
GO
